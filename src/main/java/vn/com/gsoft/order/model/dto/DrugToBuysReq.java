package vn.com.gsoft.order.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DrugToBuysReq extends BaseRequest {

    private String storeCode;
    private Integer drugId;
    private BigDecimal quantity;
    private Integer unitId;
    private Date created;
    private Date completeDate;
    private Integer staffUserId;
    private Integer statusId;
    private Integer receiptNoteId;
    private BigDecimal inPrice;
    private String description;
    private Integer archivedId;
}

