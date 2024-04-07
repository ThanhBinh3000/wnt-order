package vn.com.gsoft.order.model.dto;

import lombok.Data;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrdersReq extends BaseRequest {
    private Long invoiceId;
    private String secureCode;
    private BigDecimal totalAmount;
    private BigDecimal totalPriceWithoutPromotion;
    private Integer vat;
    private Long promotionId;
    private Long paymentMethodId;
    private Boolean isPayed;
    private Date completedDate;
    private Date payedDate;
    private BigDecimal discount;
    private Long userId;
    private String drugStoreId;
    private String supplierDrugStoreId;
    private Long orderStatusId;
    private Date orderDate;
    private BigDecimal paymentAmount;
    private Boolean isDebt;
    private String supplierDescription;
    private String description;
    private Integer orderNumber;
    private Long noteId;
    private Boolean shoppingCart;
    private Long deliveryNoteId;
    private Long receiptNoteId;
    private Long targetCombinedOrderId;
    private String sourceCombinedOrderIds;
    private Long storeId;
    private Long supplierStoreId;
    private Long noteTypeId;
    private Long customerId;
    private String customerAddress;
    private String customerFullName;
    private String customerPhone;
    private String customerCode;
}
