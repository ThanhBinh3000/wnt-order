package vn.com.gsoft.order.model.dto;

import lombok.Data;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;

@Data
public class PickUpOrderDetailReq extends BaseRequest {
    private Long id;
    private Long orderId;
    private Integer itemOrder;
    private Long drugId;
    private Long unitId;
    private BigDecimal price;
    private BigDecimal inPrice;
    private BigDecimal quantity;
    private String drugStoreId;
    private BigDecimal preRetailQuantity;
    private Long drugToBuyId;
    private Long archivedId;
    private Long referenceId;
}
