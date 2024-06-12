package vn.com.gsoft.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PickUpOrderDetail")
public class PickUpOrderDetail extends BaseEntity{
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "OrderId")
    private Long orderId;
    @Column(name = "ItemOrder")
    private Integer itemOrder;
    @Column(name = "DrugId")
    private Long drugId;
    @Column(name = "UnitId")
    private Long unitId;
    @Column(name = "Price")
    private BigDecimal price;
    @Column(name = "InPrice")
    private BigDecimal inPrice;
    @Column(name = "Quantity")
    private BigDecimal quantity;
    @Column(name = "DrugStoreId")
    private String drugStoreId;
    @Column(name = "PreRetailQuantity")
    private BigDecimal preRetailQuantity;
    @Column(name = "DrugToBuy_Id")
    private Long drugToBuyId;
    @Column(name = "ArchivedId")
    private Long archivedId;
    @Column(name = "ReferenceId")
    private Long referenceId;

    @Transient
    private DrugToBuys drugToBuys;

    @Transient
    private Thuocs thuocs;

    @Transient
    private PickUpOrder pickUpOrder;

    @Transient
    private String unitName;

    @Transient
    private BigDecimal receiptQuantity = BigDecimal.ZERO;
    @Transient
    private BigDecimal remainQuantity = BigDecimal.ZERO;

    @Transient
    private BigDecimal deliveryQuantity = BigDecimal.ZERO;
    @Transient
    private String maThuoc;
    @Transient
    private String tenThuoc;
    @Transient
    private String tenNhomThuoc;
    @Transient
    private List<DonViTinhs> unitList = new ArrayList<>();

}

