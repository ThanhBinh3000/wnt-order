package vn.com.gsoft.order.service;


import org.springframework.data.domain.Page;
import vn.com.gsoft.order.entity.Orders;
import vn.com.gsoft.order.entity.PickUpOrder;
import vn.com.gsoft.order.entity.PickUpOrderDetail;
import vn.com.gsoft.order.model.dto.PickUpOrderReq;

import java.util.List;

public interface PickUpOrderService extends BaseService<PickUpOrder, PickUpOrderReq, Long> {

    List<PickUpOrderDetail> assignStaff(PickUpOrderReq req) throws Exception;
    Page<PickUpOrder> searchPageAssginStaff(PickUpOrderReq req) throws Exception;
    PickUpOrder init(Long id) throws Exception;
    PickUpOrder create(PickUpOrderReq id) throws Exception;
    PickUpOrder update(PickUpOrderReq id) throws Exception;
    PickUpOrder detail(Long id) throws Exception;

}