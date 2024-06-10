package vn.com.gsoft.order.model.dto;

import lombok.Data;
import vn.com.gsoft.order.model.system.BaseRequest;

@Data
public class NhomThuocsReq extends BaseRequest {

    private String tenNhomThuoc;
    private String kyHieuNhomThuoc;
    private String maNhaThuoc;
    private Boolean active;
    private Boolean referenceId;
    private Long archivedId;
    private Long storeId;
    private Long typeGroupProduct;
}
