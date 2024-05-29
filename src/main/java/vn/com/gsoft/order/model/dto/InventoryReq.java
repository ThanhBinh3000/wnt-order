package vn.com.gsoft.order.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InventoryReq  {
    private Long id;
    private String drugStoreID;
    private Long drugID;
    private Float lastValue;
    private Integer drugUnitID;
    private Boolean needUpdate;
    private Float lastInPrice;
    private Float lastOutPrice;
    private Float retailOutPrice;
    private Float retailBatchOutPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastUpdated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastIncurredData;
    private String serialNumber;
    private Boolean regenRevenue;
    private Integer archiveDrugId;
    private Integer archiveUnitId;
    private Boolean hasTransactions;
    private Integer receiptItemCount;
    private Integer deliveryItemCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date expiredDate;
    private BigDecimal initValue;
    private BigDecimal initOutPrice;
    private BigDecimal initInPrice;
    private Integer storeId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date archivedDate;
    private BigDecimal outPrice;
    private Long recordStatusId;
}
