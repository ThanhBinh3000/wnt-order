package vn.com.gsoft.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PickUpOrderDetail")
public class PickUpOrderDetail extends BaseEntity{
    @Id
    @Column(name = "Id")
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
}

