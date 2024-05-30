package vn.com.gsoft.order.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

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
    private Long id;
    @Column(name = "StoreCode")
    private String storeCode;
    @Column(name = "DrugId")
    private Long drugId;
    @Column(name = "Quantity")
    private BigDecimal quantity;
    @Column(name = "UnitId")
    private Long unitId;
    @Column(name = "CompleteDate")
    private Date completeDate;
    @Column(name = "Staff_UserId")
    private Long staffUserId;
    @Column(name = "StatusId")
    private Long statusId;
    @Column(name = "ReceiptNoteId")
    private Integer receiptNoteId;
    @Column(name = "InPrice")
    private BigDecimal inPrice;
    @Column(name = "Description")
    private String description;
    @Column(name = "ArchivedId")
    private Integer archivedId;

    @Transient
    private String statusName;

    @Transient
    private String staffUserName;

    public String getStatusName() {
        if (statusId == 1) {
            return "Đã hoàn thành";
        }
        else if (statusId == 2) {
            return "Đã huỷ";
        }
        else {
            return "Chưa hoàn thành";
        }
    }
}

