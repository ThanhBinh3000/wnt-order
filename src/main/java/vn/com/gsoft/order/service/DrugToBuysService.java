package vn.com.gsoft.order.service;

import vn.com.gsoft.order.entity.DrugToBuys;
import vn.com.gsoft.order.model.dto.DrugToBuysReq;

public interface DrugToBuysService extends BaseService<DrugToBuys, DrugToBuysReq, Long> {

    DrugToBuys cancelDrugBuy(DrugToBuysReq req) throws Exception;
    DrugToBuys restoreDrugBuy(DrugToBuysReq req) throws Exception;
    DrugToBuys completeDrugToBuy(DrugToBuysReq req) throws Exception;

}