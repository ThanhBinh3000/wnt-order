package vn.com.gsoft.order.model.dto;

import lombok.Data;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PickUpOrderDetailReq extends BaseRequest {
    private Long id;
    private Long orderId;
    private Integer itemOrder;
    private Long drugId;
    private Long staffUserId;
    private Long statusId;
    private Long orderNumber;
    private Long unitId;
    private BigDecimal price;
    private BigDecimal inPrice;
    private BigDecimal quantity;
    private String drugStoreId;
    private BigDecimal preRetailQuantity;
    private Long drugToBuyId;
    private Long archivedId;
    private Long referenceId;
    private List<Long> ids;
    private List<Long> orderStatusIds;
    private Date fromDate;
    private Date toDate;

    private Long pickUpOrderId;
    private Long phieuNhapId;
}
