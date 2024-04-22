package vn.com.gsoft.order.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "DrugToBuys")
public class DrugToBuys extends BaseEntity {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "StoreCode")
    private String storeCode;
    @Column(name = "DrugId")
    private Integer drugId;
    @Column(name = "Quantity")
    private BigDecimal quantity;
    @Column(name = "UnitId")
    private Integer unitId;
    @Column(name = "Created")
    private Date created;
    @Column(name = "CompleteDate")
    private Date completeDate;
    @Column(name = "Staff_UserId")
    private Integer staffUserId;
    @Column(name = "StatusId")
    private Integer statusId;
    @Column(name = "ReceiptNoteId")
    private Integer receiptNoteId;
    @Column(name = "InPrice")
    private BigDecimal inPrice;
    @Column(name = "Description")
    private String description;
    @Column(name = "ArchivedId")
    private Integer archivedId;
}

