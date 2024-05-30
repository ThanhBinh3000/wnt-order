package vn.com.gsoft.order.service;


import org.springframework.data.domain.Page;
import vn.com.gsoft.order.entity.PickUpOrderDetail;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;

public interface PickUpOrderDetailService extends BaseService<PickUpOrderDetail, PickUpOrderDetailReq, Long> {

    Page<PickUpOrderDetail> searchPageDeliveryConfirm(PickUpOrderDetailReq req) throws Exception;

}