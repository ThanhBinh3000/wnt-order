package vn.com.gsoft.order.service.impl;

import jakarta.persistence.Tuple;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.constant.OrderStatusId;
import vn.com.gsoft.order.constant.RecordStatusContains;
import vn.com.gsoft.order.entity.*;
import vn.com.gsoft.order.model.dto.OrderDetailsReq;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;
import vn.com.gsoft.order.model.dto.PickUpOrderReq;
import vn.com.gsoft.order.model.dto.UserProfileReq;
import vn.com.gsoft.order.model.system.Profile;
import vn.com.gsoft.order.repository.*;
import vn.com.gsoft.order.service.PickUpOrderDetailService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class PickUpOrderDetailServiceImpl extends BaseServiceImpl<PickUpOrderDetail, PickUpOrderDetailReq, Long> implements PickUpOrderDetailService {

    private PickUpOrderDetailRepository hdrRepo;

    private PickUpOrderRepository pickUpOrderRepository;

    private KhachHangsRepository khachHangsRepository;

    private UserProfileRepository userProfileRepository;

    private DrugToBuysRepository drugToBuysRepository;
    private ThuocsRepository thuocsRepository;
    private InventoryRepository inventoryRepository;
    private DonViTinhsRepository donViTinhsRepository;
    private PhieuNhapChiTietsRepository phieuNhapChiTietsRepository;
    private PhieuNhapsRepository phieuNhapsRepository;
    private PhieuXuatChiTietsRepository phieuXuatChiTietsRepository;
    private PhieuXuatsRepository phieuXuatsRepository;

    @Autowired
    public PickUpOrderDetailServiceImpl(PickUpOrderDetailRepository hdrRepo,
                                        KhachHangsRepository khachHangsRepository,
                                        UserProfileRepository userProfileRepository,
                                        DrugToBuysRepository drugToBuysRepository,
                                        ThuocsRepository thuocsRepository,
                                        InventoryRepository inventoryRepository,
                                        DonViTinhsRepository donViTinhsRepository,
                                        PickUpOrderRepository pickUpOrderRepository,
                                        PhieuNhapsRepository phieuNhapsRepository,
                                        PhieuNhapChiTietsRepository phieuNhapChiTietsRepository,
                                        PhieuXuatsRepository phieuXuatsRepository,
                                        PhieuXuatChiTietsRepository phieuXuatChiTietsRepository) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
        this.khachHangsRepository = khachHangsRepository;
        this.userProfileRepository = userProfileRepository;
        this.drugToBuysRepository = drugToBuysRepository;
        this.thuocsRepository = thuocsRepository;
        this.inventoryRepository = inventoryRepository;
        this.donViTinhsRepository = donViTinhsRepository;
        this.pickUpOrderRepository = pickUpOrderRepository;
        this.phieuNhapsRepository = phieuNhapsRepository;
        this.phieuNhapChiTietsRepository = phieuNhapChiTietsRepository;
        this.phieuXuatsRepository = phieuXuatsRepository;
        this.phieuXuatChiTietsRepository = phieuXuatChiTietsRepository;
    }

    @Override
    public Page<PickUpOrderDetail> searchPage(PickUpOrderDetailReq req) throws Exception {

        Profile userInfo = this.getLoggedUser();
        if (userInfo == null){
            throw new Exception("Bad request.");
        }
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        req.setDrugStoreId(userInfo.getNhaThuoc().getMaNhaThuoc());
        req.setOrderStatusIds(Arrays.asList(OrderStatusId.BUYER_NEW,OrderStatusId.ORDER_UPDATED));
        Page<PickUpOrderDetail> pickUpOrders = hdrRepo.searchPageCustom(req, pageable);

        for (PickUpOrderDetail order : pickUpOrders.getContent()) {

            Optional<DonViTinhs> byId3 = donViTinhsRepository.findById(order.getUnitId());
            byId3.ifPresent(donViTinhs -> order.setUnitName(donViTinhs.getTenDonViTinh()));
            Optional<PickUpOrder> byId2 = pickUpOrderRepository.findById(order.getOrderId());
            byId2.ifPresent(order::setPickUpOrder);
            Optional<Thuocs> byId = thuocsRepository.findById(order.getDrugId());
            byId.ifPresent(order::setThuocs);
            Optional<DrugToBuys> byId1 = drugToBuysRepository.findById(order.getDrugToBuyId());
            if(byId1.isPresent()){
                if(byId1.get().getStaffUserId() != null && byId1.get().getStaffUserId() > 0 ){
                    Optional<UserProfile> byId4 = userProfileRepository.findById(byId1.get().getStaffUserId());
                    byId4.ifPresent(userProfile -> byId1.get().setStaffUserName(userProfile.getTenDayDu()));
                }else{
                    byId1.get().setStaffUserName("Chưa gắn nhân viên");
                }
                order.setDrugToBuys(byId1.get());
            }
        }
        return pickUpOrders;
    }


    @Override
    public Page<PickUpOrderDetail> searchPageDeliveryConfirm(PickUpOrderDetailReq req) throws Exception {
            Profile userInfo = this.getLoggedUser();
        if (userInfo == null){
            throw new Exception("Bad request.");
        }
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        req.setDrugStoreId(userInfo.getNhaThuoc().getMaNhaThuoc());
        req.setOrderStatusIds(Arrays.asList(OrderStatusId.COMMPLETED));
        Page<PickUpOrderDetail> pickUpOrders = hdrRepo.searchPageDeliveryConfirm(req, pageable);

        for (PickUpOrderDetail order : pickUpOrders.getContent()) {
            // Set Đơn vị Tính
            Optional<DonViTinhs> dviTinhOpt = donViTinhsRepository.findById(order.getUnitId());
            dviTinhOpt.ifPresent(donViTinhs -> order.setUnitName(donViTinhs.getTenDonViTinh()));

            //Set deliveryQuantity
            Optional<PickUpOrder> pickUpOrderOpt = pickUpOrderRepository.findById(order.getOrderId());
            if(pickUpOrderOpt.isPresent()){
                PickUpOrder pickUpOrder = pickUpOrderOpt.get();
                order.setPickUpOrder(pickUpOrder);
                req.setPickUpOrderId(pickUpOrder.getId());
                List<PhieuXuatChiTiets> phieuNhapCtiet = phieuXuatChiTietsRepository.searchListByPickUpOrder(req);
                BigDecimal reduce = phieuNhapCtiet.stream().map(x -> x.getSoLuong()) // Chọn cột để tính tổng (ví dụ là cột 2)
                        .reduce(BigDecimal.valueOf(0), BigDecimal::add);
                order.setDeliveryQuantity(reduce);
            }

            //Set thuocs;
            Optional<Thuocs> thuocOpt = thuocsRepository.findById(order.getDrugId());
            thuocOpt.ifPresent(order::setThuocs);

            // Set receiptQuantity and InPrice
            Optional<DrugToBuys> byId1 = drugToBuysRepository.findById(order.getDrugToBuyId());
            if(byId1.isPresent()){
                if(byId1.get().getStaffUserId() != null && byId1.get().getStaffUserId() > 0 ){
                    Optional<UserProfile> byId4 = userProfileRepository.findById(byId1.get().getStaffUserId());
                    byId4.ifPresent(userProfile -> byId1.get().setStaffUserName(userProfile.getTenDayDu()));
                }else{
                    byId1.get().setStaffUserName("Chưa gắn nhân viên");
                }
                order.setDrugToBuys(byId1.get());
                req.setPhieuNhapId(byId1.get().getId());
                List<PhieuNhapChiTiets> phieuNhapCtiet = phieuNhapChiTietsRepository.searchListByPhieuNhapId(req);
                if(!phieuNhapCtiet.isEmpty()){
                    BigDecimal reduce = phieuNhapCtiet.stream().map(x -> x.getSoLuong()) // Chọn cột để tính tổng (ví dụ là cột 2)
                            .reduce(BigDecimal.valueOf(0), BigDecimal::add);
                    order.setReceiptQuantity(reduce);
                    order.setInPrice(phieuNhapCtiet.get(0).getGiaNhap());
                }
            }
        }
        return pickUpOrders;
    }

}
