package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.entity.Orders;
import vn.com.gsoft.order.model.dto.OrdersReq;
import vn.com.gsoft.order.repository.OrdersRepository;
import vn.com.gsoft.order.service.OrdersService;


@Service
@Log4j2
public class OrdersServiceImpl extends BaseServiceImpl<Orders, OrdersReq, Long> implements OrdersService {

    private OrdersRepository hdrRepo;

    @Autowired
    public OrdersServiceImpl(OrdersRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
