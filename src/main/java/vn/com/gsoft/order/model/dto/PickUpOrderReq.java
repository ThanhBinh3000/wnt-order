package vn.com.gsoft.order.model.dto;

import lombok.Data;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PickUpOrderReq extends BaseRequest {
    private Long id;
    private Integer orderNumber;
    private String description;
    private BigDecimal totalAmount;
    private BigDecimal paymentAmount;
    private String drugStoreId;
    private Long cusId;
    private Date orderDate;
    private Long archivedId;
}
