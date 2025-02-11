package vn.com.gsoft.order.model.dto;

import jakarta.persistence.Column;
import lombok.Data;
import vn.com.gsoft.order.entity.OrderDetails;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrdersReq extends BaseRequest {
    private Long invoiceId;
    private Long id;
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
    private String drugId;
    private String supplierDrugStoreId;
    private Long orderStatusId;
    private Date orderDate;
    private BigDecimal paymentAmount;
    private Boolean isDebt;
    private Date createdDate;
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
    private String name;
    private Long recordStatusId;
    private List<OrderDetails> chiTiets = new ArrayList<>();
}
