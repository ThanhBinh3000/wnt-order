package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.entity.DrugToBuys;
import vn.com.gsoft.order.model.dto.DrugToBuysReq;
import vn.com.gsoft.order.repository.DrugToBuysRepository;
import vn.com.gsoft.order.service.DrugToBuysService;


@Service
@Log4j2
public class DrugToBuysServiceImpl extends BaseServiceImpl<DrugToBuys, DrugToBuysReq,Long> implements DrugToBuysService {

	private DrugToBuysRepository hdrRepo;
	@Autowired
	public DrugToBuysServiceImpl(DrugToBuysRepository hdrRepo) {
		super(hdrRepo);
		this.hdrRepo = hdrRepo;
	}

}
