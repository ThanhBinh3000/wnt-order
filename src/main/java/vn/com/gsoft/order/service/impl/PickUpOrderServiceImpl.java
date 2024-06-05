package vn.com.gsoft.order.service.impl;

import jakarta.persistence.Tuple;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.constant.EDrugToBuyStatus;
import vn.com.gsoft.order.constant.OrderStatusId;
import vn.com.gsoft.order.constant.RecordStatusContains;
import vn.com.gsoft.order.entity.*;
import vn.com.gsoft.order.model.dto.*;
import vn.com.gsoft.order.model.system.Profile;
import vn.com.gsoft.order.repository.*;
import vn.com.gsoft.order.service.PickUpOrderService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
public class PickUpOrderServiceImpl extends BaseServiceImpl<PickUpOrder, PickUpOrderReq, Long> implements PickUpOrderService {

    private PickUpOrderRepository hdrRepo;

    private PickUpOrderDetailRepository dtlRepo;

    private KhachHangsRepository khachHangsRepository;
    private DonViTinhsRepository donViTinhsRepository;
    
    private UserProfileRepository userProfileRepository;

    private DrugToBuysRepository drugToBuysRepository;
    private ThuocsRepository thuocsRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public PickUpOrderServiceImpl(PickUpOrderRepository hdrRepo,
                                  PickUpOrderDetailRepository dtlRepo,
                                  KhachHangsRepository khachHangsRepository,
                                  UserProfileRepository userProfileRepository,
                                  DonViTinhsRepository donViTinhsRepository,
                                  DrugToBuysRepository drugToBuysRepository,
                                  ThuocsRepository thuocsRepository,
                                  InventoryRepository inventoryRepository) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.dtlRepo = dtlRepo;
        this.khachHangsRepository = khachHangsRepository;
        this.userProfileRepository = userProfileRepository;
        this.drugToBuysRepository = drugToBuysRepository;
        this.thuocsRepository = thuocsRepository;
        this.donViTinhsRepository = donViTinhsRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Page<PickUpOrder> searchPage(PickUpOrderReq req) throws Exception {

        Profile userInfo = this.getLoggedUser();
        if (userInfo == null){
            throw new Exception("Bad request.");
        }
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        req.setDrugStoreId(userInfo.getNhaThuoc().getMaNhaThuoc());
        Page<PickUpOrder> pickUpOrders = hdrRepo.searchPage(req, pageable);

        UserProfileReq req1 = new UserProfileReq();
        req1.setMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        req1.setHoatDong(true);
        List<UserProfile> listUserProfile = userProfileRepository.searchList(req1);

        for (PickUpOrder order : pickUpOrders.getContent()) {
            List<PickUpOrderDetail> allByOrderId = dtlRepo.findAllByOrderIdAndRecordStatusId(order.getId(),RecordStatusContains.ACTIVE);
            List<Long> collect = allByOrderId.stream().map(PickUpOrderDetail::getDrugId) // Extract the name field
                    .distinct()           // Get distinct values
                    .toList();
            order.setDrugCount(collect.size());
            Optional<KhachHangs> byId = khachHangsRepository.findById(order.getCusId());
            byId.ifPresent(khachHangs -> order.setCusName(khachHangs.getTenKhachHang()));


            Optional<UserProfile> userProfileOptional = userProfileRepository.findById(order.getCreatedByUserId());
            userProfileOptional.ifPresent(userProfile -> order.setCreateUserName(userProfile.getTenDayDu()));

            order.setListUserProfile(listUserProfile);

            PickUpOrderDetailReq detailReq = new PickUpOrderDetailReq();
            detailReq.setIds(allByOrderId.stream().map(PickUpOrderDetail::getId) // Extract the name field
                    .distinct()           // Get distinct values
                    .toList());
            Optional<Tuple> tupleOpt = dtlRepo.searchStaffAssign(detailReq);
            if(tupleOpt.isPresent()){
                Tuple tuple1 = tupleOpt.get();
                Object o = tuple1.get(1);
                order.setStaffAssignId(Long.parseLong(o.toString()));
            }
        }
        return pickUpOrders;
    }

    @Override
    public  List<PickUpOrderDetail> assignStaff(PickUpOrderReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null){
            throw new Exception("Bad request.");
        }
        if(req.getId() == null || req.getId() < 1){
            throw new Exception("Order iD không được trống");
        }
        if(req.getStaffUserId() == null || req.getStaffUserId() < 1){
            throw new Exception("Staff id không được trống");
        }

        List<PickUpOrderDetail> listDetail = dtlRepo.findAllByOrderIdAndRecordStatusId(req.getId(),RecordStatusContains.ACTIVE);
        for (PickUpOrderDetail detail:listDetail) {
            Optional<Thuocs> thuocsOptional = thuocsRepository.findById(detail.getDrugId());
            if(thuocsOptional.isPresent()){
                Thuocs thuocs = thuocsOptional.get();

                InventoryReq inventoryReq = new InventoryReq();
                inventoryReq.setDrugID(thuocs.getId());
                inventoryReq.setDrugStoreID(thuocs.getNhaThuocMaNhaThuoc());
                inventoryReq.setRecordStatusId(RecordStatusContains.ACTIVE);
                Optional<Inventory> inventory = inventoryRepository.searchDetail(inventoryReq);
                if(inventory.isPresent() && detail.getQuantity().compareTo(inventory.get().getLastValue())>0){
                    if(detail.getDrugToBuyId() != null && detail.getDrugToBuyId() > 0){
                        Optional<DrugToBuys> byIdAndStatusId = drugToBuysRepository.findByIdAndStatusId(detail.getDrugToBuyId(), EDrugToBuyStatus.NotComplete);
                        if(byIdAndStatusId.isPresent()){
                            byIdAndStatusId.get().setStaffUserId(req.getStaffUserId());
                            drugToBuysRepository.save(byIdAndStatusId.get());
                            detail.setDrugToBuys(byIdAndStatusId.get());
                        }
                    }
                }
                else{
                    DrugToBuys drugToBuys = new DrugToBuys();
                    drugToBuys.setStoreCode(userInfo.getNhaThuoc().getMaNhaThuoc());
                    drugToBuys.setDrugId(detail.getDrugId());
                    drugToBuys.setUnitId(detail.getUnitId());
                    drugToBuys.setCreated(new Date());
                    drugToBuys.setStaffUserId(req.getStaffUserId());
                    drugToBuys.setStatusId(EDrugToBuyStatus.NotComplete);
                    drugToBuys.setRecordStatusId(RecordStatusContains.ACTIVE);
                    drugToBuysRepository.save(drugToBuys);

                    detail.setDrugToBuyId(drugToBuys.getId());
                    detail.setDrugToBuys(drugToBuys);
                    dtlRepo.save(detail);
                }
            }
        }
        return listDetail;
    }

    @Override
    public Page<PickUpOrder> searchPageAssginStaff(PickUpOrderReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null){
            throw new Exception("Bad request.");
        }
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        req.setDrugStoreId(userInfo.getNhaThuoc().getMaNhaThuoc());
        req.setOrderStatusIds(Arrays.asList(OrderStatusId.BUYER_NEW,OrderStatusId.ORDER_UPDATED));
        Page<PickUpOrder> pickUpOrders = hdrRepo.searchPage(req, pageable);

        UserProfileReq req1 = new UserProfileReq();
        req1.setMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
        req1.setHoatDong(true);
        List<UserProfile> listUserProfile = userProfileRepository.searchList(req1);

        for (PickUpOrder order : pickUpOrders.getContent()) {
            List<PickUpOrderDetail> allByOrderId = dtlRepo.findAllByOrderIdAndRecordStatusId(order.getId(),RecordStatusContains.ACTIVE);
            List<Long> collect = allByOrderId.stream().map(PickUpOrderDetail::getDrugId) // Extract the name field
                    .distinct()           // Get distinct values
                    .toList();
            order.setDrugCount(collect.size());
            Optional<KhachHangs> byId = khachHangsRepository.findById(order.getCusId());
            byId.ifPresent(khachHangs -> order.setCusName(khachHangs.getTenKhachHang()));


            order.setListUserProfile(listUserProfile);

            PickUpOrderDetailReq detailReq = new PickUpOrderDetailReq();
            detailReq.setIds(allByOrderId.stream().map(PickUpOrderDetail::getId) // Extract the name field
                    .distinct()           // Get distinct values
                    .toList());
            Optional<Tuple> tupleOpt = dtlRepo.searchStaffAssign(detailReq);
            if(tupleOpt.isPresent()){
                Tuple tuple1 = tupleOpt.get();
                Object o = tuple1.get(1);
                order.setStaffAssignId(Long.parseLong(o.toString()));
            }
        }
        return pickUpOrders;
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");

        Optional<PickUpOrder> optional = hdrRepo.findById(id);
        if (optional.isEmpty()) {
            throw new Exception("Không tìm thấy dữ liệu.");
        }
        optional.get().setRecordStatusId(RecordStatusContains.DELETED);
        hdrRepo.save(optional.get());

        List<PickUpOrderDetail> allByOrderId = dtlRepo.findAllByOrderId(optional.get().getId());
        if(!allByOrderId.isEmpty()){
            allByOrderId.forEach(item -> {
                item.setRecordStatusId(RecordStatusContains.DELETED);
            });
            dtlRepo.saveAll(allByOrderId);
        }

        return true;
    }

    @Override
    public PickUpOrder init(Long id) throws Exception {
        PickUpOrder data = null;
        Integer orderNumberMax = hdrRepo.findOrderNumberMax();
        if (id == null) {
            data = new PickUpOrder();
            data.setOrderNumber(orderNumberMax + 1);
            data.setOrderDate(new Date());
        } else {
            Optional<PickUpOrder> phieuNhaps = hdrRepo.findById(id);
            if (phieuNhaps.isPresent()) {
                data = phieuNhaps.get();
                data.setId(null);
//                data.setOrderNumber(orderNumberMax);
                data.setOrderDate(new Date());
                data.setCreatedByUserId(null);
                data.setModifiedByUserId(null);
                data.setCreated(null);
                data.setModified(null);
            } else {
                throw new Exception("Không tìm thấy phiếu copy!");
            }
        }
        return data;
    }

    @Override
    public PickUpOrder create(PickUpOrderReq req) throws Exception{
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Double totalAmount = 0D;
        PickUpOrder hdr = new PickUpOrder();
        BeanUtils.copyProperties(req, hdr, "id");
        hdr.setCreated(new Date());
        hdr.setUpdated(new Date());
        hdr.setOrderStatusId(vn.com.gsoft.order.entity.OrderStatusId.BUYER_NEW.getValue());
        hdr.setCreatedByUserId(getLoggedUser().getId());
        hdr.setUpdatedByUserId(getLoggedUser().getId());
        hdr.setDrugStoreId(userInfo.getNhaThuoc().getMaNhaThuoc());
//        totalAmount = req.getChiTiets().stream().mapToDouble(x -> Double.valueOf(String.valueOf(x.getQuantity()))).sum();
//        hdr.setTotalAmount(BigDecimal.valueOf(totalAmount));
        PickUpOrder orders = hdrRepo.save(hdr);
        List<PickUpOrderDetail> orderDetails = saveChildren(orders.getId(), req);
        orders.setChiTiets(orderDetails);
        return orders;
    }

    private List<PickUpOrderDetail> saveChildren(Long idHdr, PickUpOrderReq req) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        // save chi tiết
        dtlRepo.deleteAllByOrderId(idHdr);
        for (PickUpOrderDetail chiTiet : req.getChiTiets()) {
            chiTiet.setOrderId(idHdr);
            chiTiet.setCreated(new Date());
            chiTiet.setCreatedByUserId(getLoggedUser().getId());
            chiTiet.setRecordStatusId(RecordStatusContains.ACTIVE);
            chiTiet.setDrugStoreId(userInfo.getNhaThuoc().getMaNhaThuoc());
        }
        dtlRepo.saveAll(req.getChiTiets());
        return req.getChiTiets();
    }

    @Override
    public PickUpOrder update(PickUpOrderReq req) throws Exception{
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        PickUpOrder object = new PickUpOrder();
        Optional<PickUpOrder> hdr = hdrRepo.findById(req.getId());
        if(hdr.isPresent()){
            object = hdr.get();
            BeanUtils.copyProperties(req, object, "id");
            object.setOrderStatusId(vn.com.gsoft.order.entity.OrderStatusId.BUYER_NEW.getValue());
            object.setUpdated(new Date());

            PickUpOrder orders = hdrRepo.save(object);
            List<PickUpOrderDetail> orderDetails = saveChildren(orders.getId(), req);
            orders.setChiTiets(orderDetails);
        }
        return object;
    }

    @Override
    public PickUpOrder detail(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<PickUpOrder> hdr = Optional.empty();
        hdr = hdrRepo.findById(id);
        if(hdr.isPresent()){
            PickUpOrder object = hdr.get();
            Optional<KhachHangs> byId = khachHangsRepository.findById(object.getCusId());
            byId.ifPresent(khachHangs -> object.setCusName(khachHangs.getTenKhachHang()));
            List<PickUpOrderDetail> dtl = new ArrayList<>();
            dtl = dtlRepo.findAllByOrderId(hdr.get().getId());
            if(dtl.size() > 0){
                dtl.forEach(item -> {
                    Optional<Thuocs> thuocsOptional = thuocsRepository.findById(item.getDrugId());
                    item.setMaThuoc(thuocsOptional.get().getMaThuoc());
                    item.setTenThuoc(thuocsOptional.get().getTenThuoc());
                    Optional<DonViTinhs> byId3 = donViTinhsRepository.findById(item.getUnitId());
                    byId3.ifPresent(donViTinhs -> item.getUnitList().add(donViTinhs));
                });
                hdr.get().setChiTiets(dtl);
            }
        }
        return hdr.get();
    }
}
