package vn.com.gsoft.order.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.gsoft.order.constant.EDrugToBuyStatus;
import vn.com.gsoft.order.constant.RecordStatusContains;
import vn.com.gsoft.order.entity.DrugToBuys;
import vn.com.gsoft.order.entity.PhieuNhapChiTiets;
import vn.com.gsoft.order.entity.PhieuNhaps;
import vn.com.gsoft.order.entity.PickUpOrderDetail;
import vn.com.gsoft.order.model.dto.DrugToBuysReq;
import vn.com.gsoft.order.model.system.Profile;
import vn.com.gsoft.order.repository.DrugToBuysRepository;
import vn.com.gsoft.order.repository.PhieuNhapChiTietsRepository;
import vn.com.gsoft.order.repository.PhieuNhapsRepository;
import vn.com.gsoft.order.repository.PickUpOrderDetailRepository;
import vn.com.gsoft.order.service.DrugToBuysService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class DrugToBuysServiceImpl extends BaseServiceImpl<DrugToBuys, DrugToBuysReq,Long> implements DrugToBuysService {

	private DrugToBuysRepository hdrRepo;

	private PickUpOrderDetailRepository pickUpOrderDetailRepository;

	private PhieuNhapsRepository phieuNhapsRepository;

	private PhieuNhapChiTietsRepository phieuNhapChiTietsRepository;
	@Autowired
	public DrugToBuysServiceImpl(DrugToBuysRepository hdrRepo,
								 PickUpOrderDetailRepository pickUpOrderDetailRepository,
								 PhieuNhapsRepository phieuNhapsRepository,
								 PhieuNhapChiTietsRepository phieuNhapChiTietsRepository) {
		super(hdrRepo);
		this.hdrRepo = hdrRepo;
		this.pickUpOrderDetailRepository = pickUpOrderDetailRepository;
		this.phieuNhapsRepository = phieuNhapsRepository;
		this.phieuNhapChiTietsRepository = phieuNhapChiTietsRepository;
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
				tryToUpdateReceiptNoteFromDrugToBuy(byId.get(),req.getPickUpOrderDetailId());
				return byId.get();
			}
		}


		return null;
	}


	public void tryToUpdateReceiptNoteFromDrugToBuy(DrugToBuys drugToBuys,Long pickUpOrderDetailId) throws Exception {
		Profile userInfo = this.getLoggedUser();
		if (userInfo == null){
			throw new Exception("Bad request.");
		}
		if(drugToBuys.getReceiptNoteId() > 0){
			List<PhieuNhapChiTiets> phieuNhapCtiet = phieuNhapChiTietsRepository.findAllByPhieuNhapMaPhieuNhapAndRecordStatusIdAndThuocThuocId(drugToBuys.getReceiptNoteId(), RecordStatusContains.ACTIVE, drugToBuys.getDrugId());
			if(!phieuNhapCtiet.isEmpty()){
				phieuNhapCtiet.forEach(item -> {
					item.setSoLuong(drugToBuys.getQuantity());
					item.setGiaNhap(drugToBuys.getInPrice());
				});
				phieuNhapChiTietsRepository.saveAll(phieuNhapCtiet);
				BigDecimal reduce = phieuNhapCtiet.stream().map(item -> item.getSoLuong().multiply(item.getGiaNhap())) // Chọn cột để tính tổng (ví dụ là cột 2)
						.reduce(BigDecimal.valueOf(0), BigDecimal::add);
				Optional<PhieuNhaps> byId = phieuNhapsRepository.findById(drugToBuys.getReceiptNoteId());
				if(byId.isPresent()){
					byId.get().setTongTien(reduce);
					byId.get().setDaTra(reduce);
					phieuNhapsRepository.save(byId.get());
				}
			}
		}else{
			Optional<PickUpOrderDetail> pickUpOrderDetailOpt = pickUpOrderDetailRepository.findByDrugToBuyId(drugToBuys.getId());
			if(pickUpOrderDetailOpt.isEmpty()){
				throw new Exception("Không tìm thấy PickUpOrderDetail theo DrugToBuyId"+drugToBuys.getId());
			}
			PickUpOrderDetail pickUpOrderDetail1 = pickUpOrderDetailOpt.get();
			Optional<PhieuNhaps> phieuNhapsOpt = phieuNhapsRepository.findByPickUpOrderId(pickUpOrderDetail1.getOrderId());
			Long idPhieuNhap = 0L;
			if(phieuNhapsOpt.isPresent()){
				idPhieuNhap = phieuNhapsOpt.get().getId();
			}else{

			}
			PhieuNhapChiTiets phieuNhapChiTiets = new PhieuNhapChiTiets();
			phieuNhapChiTiets.setDonViTinhMaDonViTinh(drugToBuys.getUnitId());
			phieuNhapChiTiets.setGiaNhap(drugToBuys.getInPrice());
			phieuNhapChiTiets.setPhieuNhapMaPhieuNhap(idPhieuNhap);
			phieuNhapChiTiets.setSoLuong(drugToBuys.getQuantity());
			phieuNhapChiTiets.setNhaThuocMaNhaThuoc(userInfo.getNhaThuoc().getMaNhaThuoc());
			phieuNhapChiTiets.setThuocThuocId(drugToBuys.getDrugId());
			phieuNhapChiTiets.setItemOrder(1);
			phieuNhapChiTiets.setIsModified(true);
			phieuNhapChiTietsRepository.save(phieuNhapChiTiets);
			drugToBuys.setReceiptNoteId(idPhieuNhap);
			hdrRepo.save(drugToBuys);

			if(phieuNhapsOpt.isPresent()){
				List<PhieuNhapChiTiets> phieuNhapCtiet = phieuNhapChiTietsRepository.findAllByPhieuNhapMaPhieuNhapAndRecordStatusId(drugToBuys.getReceiptNoteId(), RecordStatusContains.ACTIVE);
				BigDecimal reduce = phieuNhapCtiet.stream().map(item -> item.getSoLuong().multiply(item.getGiaNhap())) // Chọn cột để tính tổng (ví dụ là cột 2)
						.reduce(BigDecimal.valueOf(0), BigDecimal::add);
				phieuNhapsOpt.get().setTongTien(reduce);
				phieuNhapsOpt.get().setDaTra(reduce);
				phieuNhapsRepository.save(phieuNhapsOpt.get());
			}
			//TODO
//			MakeAffectedChangesRelatedReceiptNotes(storeCode, model.Staff_UserId, true, false, false, new int[] { noteId });
			if (pickUpOrderDetailId != null && pickUpOrderDetailId > 0)
			{
//				autoUpdateInPricePickUpOrderByItemId(drugToBuys, pickUpOrderDetailId, idPhieuNhap);
				Optional<PickUpOrderDetail> pickByIdOtp = pickUpOrderDetailRepository.findById(pickUpOrderDetailId);
				if(pickByIdOtp.isPresent()){
					pickByIdOtp.get().setInPrice(phieuNhapChiTiets.getGiaNhap());
					pickUpOrderDetailRepository.save(pickByIdOtp.get());
				}
			}
		}
	}

	private void autoUpdateInPricePickUpOrderByItemId(DrugToBuys drugToBuys,Long pickUpOrderDetailId,Long phieuNhapId){

	}
}
