package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.entity.PickUpOrderDetail;
import vn.com.gsoft.order.model.dto.PickUpOrderDetailReq;
import vn.com.gsoft.order.repository.PickUpOrderDetailRepository;
import vn.com.gsoft.order.service.PickUpOrderDetailService;


@Service
@Log4j2
public class PickUpOrderDetailServiceImpl extends BaseServiceImpl<PickUpOrderDetail, PickUpOrderDetailReq, Long> implements PickUpOrderDetailService {

    private PickUpOrderDetailRepository hdrRepo;

    @Autowired
    public PickUpOrderDetailServiceImpl(PickUpOrderDetailRepository hdrRepo) {
        super(hdrRepo);
        this.hdrRepo = hdrRepo;
    }

}
