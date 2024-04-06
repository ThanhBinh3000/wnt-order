package vn.com.gsoft.order.util.system;

import lombok.extern.slf4j.Slf4j;
import vn.com.gsoft.order.enums.EnumResponse;
import vn.com.gsoft.order.model.system.BaseResponse;

@Slf4j
public class ResponseUtils {
    public static BaseResponse ok(Object data) {
        BaseResponse resp = new BaseResponse();
        resp.setData(data);
        resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
        resp.setMessage(EnumResponse.RESP_SUCC.getDescription());
        return resp;
    }
}
