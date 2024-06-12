package vn.com.gsoft.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Orders extends BaseEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "InvoiceId")
    private Long invoiceId;
    @Column(name = "SecureCode")
    private String secureCode;
    @Column(name = "TotalAmount")
    private BigDecimal totalAmount;
    @Column(name = "TotalPriceWithoutPromotion")
    private BigDecimal totalPriceWithoutPromotion;
    @Column(name = "VAT")
    private Integer vat;
    @Column(name = "PromotionId")
    private Long promotionId;
    @Column(name = "PaymentMethodId")
    private Long paymentMethodId;
    @Column(name = "IsPayed")
    private Boolean isPayed;
    @Column(name = "CompletedDate")
    private Date completedDate;
    @Column(name = "PayedDate")
    private Date payedDate;
    @Column(name = "Discount")
    private BigDecimal discount;
    @Column(name = "UserId")
    private Long userId;
    @Column(name = "DrugStoreId")
    private String drugStoreId;
    @Column(name = "SupplierDrugStoreId")
    private String supplierDrugStoreId;
    @Column(name = "OrderStatusId")
    private Long orderStatusId;
    @Column(name = "OrderDate")
    private Date orderDate;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "PaymentAmount")
    private BigDecimal paymentAmount;
    @Column(name = "IsDebt")
    private Boolean isDebt;
    @Column(name = "SupplierDescription")
    private String supplierDescription;
    @Column(name = "Description")
    private String description;
    @Column(name = "OrderNumber")
    private Integer orderNumber;
    @Column(name = "NoteId")
    private Long noteId;
    @Column(name = "ShoppingCart")
    private Boolean shoppingCart;
    @Column(name = "DeliveryNoteId")
    private Long deliveryNoteId;
    @Column(name = "ReceiptNoteId")
    private Long receiptNoteId;
    @Column(name = "TargetCombinedOrderId")
    private Long targetCombinedOrderId;
    @Column(name = "SourceCombinedOrderIds")
    private String sourceCombinedOrderIds;
    @Column(name = "StoreId")
    private Long storeId;
    @Column(name = "SupplierStoreId")
    private Long supplierStoreId;
    @Column(name = "NoteTypeId")
    private Long noteTypeId;
    @Column(name = "CustomerId")
    private Long customerId;
//    @Lob
    @Column(name = "CustomerAddress")
    private String customerAddress;
    @Column(name = "CustomerFullName")
    private String customerFullName;
    @Column(name = "CustomerPhone")
    private String customerPhone;
    @Column(name = "CustomerCode")
    private String customerCode;
    @Column(name = "RecordStatusId")
    private Long recordStatusId;
    @Transient
    private List<OrderDetails> chiTiets;
    @Transient
    private String orderStatusText;
    @Transient
    private String staffName;
}

