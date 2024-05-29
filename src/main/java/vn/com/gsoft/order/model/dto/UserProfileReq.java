package vn.com.gsoft.order.model.dto;

import jakarta.persistence.Column;
import lombok.Data;
import vn.com.gsoft.order.model.system.BaseRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserProfileReq extends BaseRequest {

    private String maNhaThuoc;
    private Boolean hoatDong;
}
