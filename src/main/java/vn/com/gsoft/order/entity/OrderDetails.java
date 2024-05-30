package vn.com.gsoft.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetails extends BaseEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "OrderId")
    private Long orderId;
    @Column(name = "DrugId")
    private Long drugId;
    @Column(name = "UnitId")
    private Long unitId;
    @Column(name = "RetailUnitId")
    private Long retailUnitId;
    @Column(name = "Quantity")
    private BigDecimal quantity;
    @Column(name = "Price")
    private BigDecimal price;
    @Column(name = "Factors")
    private BigDecimal factors;
    @Column(name = "RealQuantity")
    private BigDecimal realQuantity;
    @Column(name = "TotalAmount")
    private BigDecimal totalAmount;
    @Column(name = "DrugStoreId")
    private String drugStoreId;
    @Column(name = "ItemOrder")
    private Integer itemOrder;
    @Column(name = "Discount")
    private BigDecimal discount;
    @Column(name = "CreatedByStoreId")
    private Long createdByStoreId;
    @Column(name = "RecordStatusId")
    private Long recordStatusId;
    @Column(name = "SupplierStoreCode")
    private String supplierStoreCode;
    @Column(name = "InPrice")
    private BigDecimal inPrice;
    @Column(name = "IsPickUpGoods")
    private Boolean isPickUpGoods;
    @Column(name="CreatedDate")
    private Date createdDate;
    @Column(name="CreatedByUserId")
    private Long createdByUserId;
    @Transient
    private String maThuoc;
    @Transient
    private String tenThuoc;
    @Transient
    private String tenDonViTinhXuatLe;
}

