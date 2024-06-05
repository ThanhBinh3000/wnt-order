package vn.com.gsoft.order.service;


import org.springframework.data.domain.Page;
import vn.com.gsoft.order.entity.OrderStatus;
import vn.com.gsoft.order.entity.Orders;
import vn.com.gsoft.order.model.dto.OrdersReq;

public interface OrderStatusService extends BaseService<OrderStatus, OrdersReq, Long> {


}