package vn.com.gsoft.order.model.dto;

import lombok.Data;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;

@Data
public class OrderDetailsReq extends BaseRequest {
    private Long orderId;
    private Long drugId;
    private Long unitId;
    private Long retailUnitId;
    private BigDecimal quantity;
    private BigDecimal price;
    private BigDecimal factors;
    private BigDecimal realQuantity;
    private BigDecimal totalAmount;
    private String drugStoreId;
    private Integer itemOrder;
    private BigDecimal discount;
    private Long createdByStoreId;
    private String supplierStoreCode;
    private BigDecimal inPrice;
    private Boolean isPickUpGoods;
}
