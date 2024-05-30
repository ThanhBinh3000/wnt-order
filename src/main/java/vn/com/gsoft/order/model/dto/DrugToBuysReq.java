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
    private Long drugId;
    private BigDecimal quantity;
    private Long unitId;
    private Date created;
    private Date completeDate;
    private Long staffUserId;
    private Long statusId;
    private Long receiptNoteId;
    private BigDecimal inPrice;
    private String description;
    private Long archivedId;
    private Date fromDate;
    private Date toDate;

    private Long pickUpOrderDetailId;
}

