package vn.com.gsoft.order.service.impl;

import jakarta.persistence.Tuple;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.constant.EDrugToBuyStatus;
import vn.com.gsoft.order.constant.OrderStatusId;
import vn.com.gsoft.order.constant.RecordStatusContains;
import vn.com.gsoft.order.entity.*;
import vn.com.gsoft.order.model.dto.InventoryReq;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;
import vn.com.gsoft.order.model.dto.PickUpOrderReq;
import vn.com.gsoft.order.model.dto.UserProfileReq;
import vn.com.gsoft.order.model.system.Profile;
import vn.com.gsoft.order.repository.*;
import vn.com.gsoft.order.service.PickUpOrderService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
public class PickUpOrderServiceImpl extends BaseServiceImpl<PickUpOrder, PickUpOrderReq, Long> implements PickUpOrderService {

    private PickUpOrderRepository hdrRepo;

    private PickUpOrderDetailRepository dtlRepo;

    private KhachHangsRepository khachHangsRepository;
    
    private UserProfileRepository userProfileRepository;

    private DrugToBuysRepository drugToBuysRepository;
    private ThuocsRepository thuocsRepository;
    private InventoryRepository inventoryRepository;

    @Autowired
    public PickUpOrderServiceImpl(PickUpOrderRepository hdrRepo,
                                  PickUpOrderDetailRepository dtlRepo,
                                  KhachHangsRepository khachHangsRepository,
                                  UserProfileRepository userProfileRepository,
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
}
