package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.entity.PickUpOrder;
import vn.com.gsoft.order.model.dto.PickUpOrderReq;
import vn.com.gsoft.order.repository.PickUpOrderRepository;
import vn.com.gsoft.order.service.PickUpOrderService;


@Service
@Log4j2
public class PickUpOrderServiceImpl extends BaseServiceImpl<PickUpOrder, PickUpOrderReq, Long> implements PickUpOrderService {

    private PickUpOrderRepository hdrRepo;

    @Autowired
    public PickUpOrderServiceImpl(PickUpOrderRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
