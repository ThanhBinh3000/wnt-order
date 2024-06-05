package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.constant.RecordStatusContains;
import vn.com.gsoft.order.entity.*;
import vn.com.gsoft.order.model.dto.OrdersReq;
import vn.com.gsoft.order.model.system.Profile;
import vn.com.gsoft.order.repository.*;
import vn.com.gsoft.order.service.OrdersService;

import java.math.BigDecimal;
import java.util.*;


@Service
@Log4j2
public class OrdersServiceImpl extends BaseServiceImpl<Orders, OrdersReq, Long> implements OrdersService {

    private OrdersRepository hdrRepo;
    @Autowired
    private OrderDetailsRepository dtlRepo;
    @Autowired
    private ThuocsRepository thuocsRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    public OrdersServiceImpl(OrdersRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }
    @Override
    public Orders create(OrdersReq req) throws Exception{
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Double totalAmount = 0D;
        Orders hdr = new Orders();
        BeanUtils.copyProperties(req, hdr, "id");
        hdr.setCreated(new Date());
        hdr.setOrderStatusId(OrderStatusId.BUYER_NEW.getValue());
        hdr.setCreatedByUserId(getLoggedUser().getId());
//        totalAmount = req.getChiTiets().stream().mapToDouble(x -> Double.valueOf(String.valueOf(x.getQuantity()))).sum();
//        hdr.setTotalAmount(BigDecimal.valueOf(totalAmount));
        Orders orders = hdrRepo.save(hdr);
        List<OrderDetails> orderDetails = saveChildren(orders.getId(), req);
        orders.setChiTiets(orderDetails);
        return orders;
    }

    @Override
    public Orders update(OrdersReq req) throws Exception{
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<Orders> hdr = Optional.empty();
        hdr = hdrRepo.findById(req.getId());
        Orders object = hdr.get();
        BeanUtils.copyProperties(req, object);
        object.setModified(new Date());
        object.setModifiedByUserId(getLoggedUser().getId());
        Orders orders = hdrRepo.save(object);
        List<OrderDetails> orderDetails = saveChildren(orders.getId(), req);
        orders.setChiTiets(orderDetails);
        return orders;
    }

    @Override
    public Orders detail(Long id) throws Exception {
        Profile userInfo = this.getLoggedUser();
        if (userInfo == null)
            throw new Exception("Bad request.");
        Optional<Orders> hdr = Optional.empty();
        hdr = hdrRepo.findById(id);
        if(hdr.isPresent()){
            List<OrderDetails> dtl = new ArrayList<>();
            dtl = dtlRepo.findAllByOrderId(hdr.get().getId());
            if(dtl.size() > 0){
                dtl.forEach(item -> {
                    Optional<Thuocs> thuocsOptional = thuocsRepository.findById(item.getDrugId());
                    item.setMaThuoc(thuocsOptional.get().getMaThuoc());
                    item.setTenThuoc(thuocsOptional.get().getTenThuoc());
//                    item.setTenDonViTinhXuatLe(thuocsOptional.get().getDonViXuatLeMaDonViTinh());
                });
                hdr.get().setChiTiets(dtl);
            }
        }
        return hdr.get();
    }

    private List<OrderDetails> saveChildren(Long idHdr, OrdersReq req) throws Exception {
        // save chi tiết
        dtlRepo.deleteAllByOrderId(idHdr);
        for (OrderDetails chiTiet : req.getChiTiets()) {
            chiTiet.setOrderId(idHdr);
            chiTiet.setCreated(new Date());
            chiTiet.setCreatedDate(new Date());
            chiTiet.setCreatedByUserId(getLoggedUser().getId());
        }
        dtlRepo.saveAll(req.getChiTiets());
        return req.getChiTiets();
    }

    @Override
    public Orders init(Long id) throws Exception {
        Orders data = null;
        if (id == null) {
            data = new Orders();
            Orders orders = hdrRepo.findIdMax();
            data.setOrderNumber(orders.getOrderNumber() + 1);
            data.setOrderDate(new Date());
        } else {
            Optional<Orders> phieuNhaps = hdrRepo.findById(id);
            if (phieuNhaps.isPresent()) {
                data = phieuNhaps.get();
                data.setId(null);
                Orders orders = hdrRepo.findIdMax();
                data.setOrderNumber(orders.getOrderNumber());
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
    public Page<Orders> searchPage(OrdersReq req) throws Exception {
        Pageable pageable = PageRequest.of(req.getPaggingReq().getPage(), req.getPaggingReq().getLimit());
        req.setRecordStatusId(RecordStatusContains.ACTIVE);
        Page<Orders> page = hdrRepo.searchPage(req, pageable);
        List<Orders> list = page.getContent();
        for (Orders orders : list) {
            if(orders.getCreatedByUserId() != null){
                Optional<UserProfile> object = userProfileRepository.findById(orders.getCreatedByUserId());
                if(object.isPresent()){
                    UserProfile userProfile = object.get();
                    orders.setStaffName(userProfile.getUserName());
                }
            }
            Optional<OrderStatus> orderStatus = orderStatusRepository.findById(orders.getOrderStatusId());
            if(orderStatus.isPresent()){
                orders.setOrderStatusText(orderStatus.get().getBuyerDisplayName());
            }
            List<OrderDetails> orderDetails = dtlRepo.findAllByOrderId(orders.getId());
            if(orderDetails.size() > 0){
                orders.setTotalAmount(BigDecimal.valueOf(orderDetails.stream().mapToDouble(x -> x.getTotalAmount() != null ? Double.valueOf(String.valueOf(x.getTotalAmount())) : 0).sum()));
                orders.setChiTiets(orderDetails);
            }
        }
        return page;
    }

    @Override
    public Orders sendOrder(OrdersReq req) throws Exception {
        Optional<Orders> data = hdrRepo.findById(req.getId());
        Orders orders = data.get();
        orders.setOrderStatusId(req.getOrderStatusId());
        Optional<OrderStatus> orderStatus = orderStatusRepository.findById(req.getOrderStatusId());
        orders.setOrderStatusText(orderStatus.get().getBuyerDisplayName());
        hdrRepo.save(orders);
        return orders;
    }

}
