package vn.com.gsoft.order.service;


import org.springframework.data.domain.Page;
import vn.com.gsoft.order.entity.Orders;
import vn.com.gsoft.order.model.dto.OrdersReq;

public interface OrdersService extends BaseService<Orders, OrdersReq, Long> {
    Orders create(OrdersReq ordersReq) throws Exception;
    Page<Orders> searchPage(OrdersReq ordersReq) throws Exception;
    Orders update(OrdersReq ordersReq) throws Exception;
    Orders detail(Long id) throws Exception;
    Orders init(Long id) throws Exception;
    Orders sendOrder(OrdersReq ordersReq) throws Exception;

}