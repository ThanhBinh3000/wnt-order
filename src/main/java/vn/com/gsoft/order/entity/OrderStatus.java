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
@Table(name = "OrderStatus")
public class OrderStatus extends BaseEntity {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Name")
    private String name;
    @Column(name = "BuyerDisplayName")
    private String buyerDisplayName;
    @Column(name = "SellerDisplayName")
    private Boolean sellerDisplayName;
    @Column(name = "ActiveForSeller")
    private Boolean activeForSeller;
}

