package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.constant.EDrugToBuyStatus;
import vn.com.gsoft.order.constant.RecordStatusContains;
import vn.com.gsoft.order.entity.DrugToBuys;
import vn.com.gsoft.order.entity.PickUpOrderDetail;
import vn.com.gsoft.order.model.dto.DrugToBuysReq;
import vn.com.gsoft.order.model.system.Profile;
import vn.com.gsoft.order.repository.DrugToBuysRepository;
import vn.com.gsoft.order.repository.PickUpOrderDetailRepository;
import vn.com.gsoft.order.service.DrugToBuysService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;


@Service
@Log4j2
public class DrugToBuysServiceImpl extends BaseServiceImpl<DrugToBuys, DrugToBuysReq,Long> implements DrugToBuysService {

	private DrugToBuysRepository hdrRepo;

	private PickUpOrderDetailRepository pickUpOrderDetailRepository;
	@Autowired
	public DrugToBuysServiceImpl(DrugToBuysRepository hdrRepo,
								 PickUpOrderDetailRepository pickUpOrderDetailRepository) {
		super(hdrRepo);
		this.hdrRepo = hdrRepo;
		this.pickUpOrderDetailRepository = pickUpOrderDetailRepository;
	}

	@Override
	public DrugToBuys cancelDrugBuy(DrugToBuysReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null){
			throw new Exception("Bad request.");
		}
		if(req.getId() <= 0){
			DrugToBuys drugToBuy = new DrugToBuys();
			drugToBuy.setStoreCode(userInfo.getNhaThuoc().getMaNhaThuoc());
			drugToBuy.setDrugId(req.getDrugId());
			drugToBuy.setUnitId(req.getUnitId());
			drugToBuy.setQuantity(BigDecimal.ZERO);
			drugToBuy.setCreated(new Date());
			drugToBuy.setStaffUserId(req.getStaffUserId());
			drugToBuy.setStatusId(EDrugToBuyStatus.Cancel);
			drugToBuy.setRecordStatusId(RecordStatusContains.ACTIVE);
			hdrRepo.save(drugToBuy);
			Optional<PickUpOrderDetail> byId = pickUpOrderDetailRepository.findById(req.getPickUpOrderDetailId());
			if(byId.isPresent()){
				byId.get().setDrugToBuyId(drugToBuy.getId());
				pickUpOrderDetailRepository.save(byId.get());
			}
			return drugToBuy;
		}else{
			Optional<DrugToBuys> byId =hdrRepo.findById(req.getId());
			if(byId.isPresent()){
				byId.get().setStatusId(EDrugToBuyStatus.Cancel);
				hdrRepo.save(byId.get());
				return byId.get();
			}
		}
		return null;
	}

	@Override
	public DrugToBuys restoreDrugBuy(DrugToBuysReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null){
			throw new Exception("Bad request.");
		}
		if(req.getId() > 0){
			Optional<DrugToBuys> byId = hdrRepo.findById(req.getId());
			if(byId.isPresent()){
				byId.get().setStatusId(EDrugToBuyStatus.NotComplete);
				hdrRepo.save(byId.get());
				return byId.get();
			}
		}
		return null;
	}

	@Override
	public DrugToBuys completeDrugToBuy(DrugToBuysReq req) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null){
			throw new Exception("Bad request.");
		}
		if(req.getId() > 0){
			Optional<DrugToBuys> byId = hdrRepo.findById(req.getId());
			if(byId.isPresent()){
				byId.get().setStatusId(EDrugToBuyStatus.Complete);
				byId.get().setCompleteDate(new Date());
				byId.get().setQuantity(req.getQuantity());
				byId.get().setInPrice(req.getInPrice());
				byId.get().setDescription(req.getDescription());
				hdrRepo.save(byId.get());
				return byId.get();
			}
		}
		return null;
	}
}
