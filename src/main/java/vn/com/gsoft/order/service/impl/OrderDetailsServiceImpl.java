package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.entity.OrderDetails;
import vn.com.gsoft.order.model.dto.OrderDetailsReq;
import vn.com.gsoft.order.repository.OrderDetailsRepository;
import vn.com.gsoft.order.service.OrderDetailsService;


@Service
@Log4j2
public class OrderDetailsServiceImpl extends BaseServiceImpl<OrderDetails, OrderDetailsReq, Long> implements OrderDetailsService {

    private OrderDetailsRepository hdrRepo;

    @Autowired
    public OrderDetailsServiceImpl(OrderDetailsRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
