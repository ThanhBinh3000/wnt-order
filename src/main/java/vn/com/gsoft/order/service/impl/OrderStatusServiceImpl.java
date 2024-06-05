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
import vn.com.gsoft.order.service.OrderStatusService;
import vn.com.gsoft.order.service.OrdersService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class OrderStatusServiceImpl extends BaseServiceImpl<OrderStatus, OrdersReq, Long> implements OrderStatusService {
    private OrderStatusRepository hdrRepo;
    @Autowired
    public OrderStatusServiceImpl(OrderStatusRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
