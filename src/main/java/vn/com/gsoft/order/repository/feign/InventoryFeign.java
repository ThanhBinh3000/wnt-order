package vn.com.gsoft.order.repository.feign;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import vn.com.gsoft.order.model.dto.InventoryReq;
import vn.com.gsoft.order.model.system.BaseResponse;

import java.util.HashMap;

@FeignClient(name = "wnt-inventory")
public interface InventoryFeign {

    @PostMapping("/inventory/search-detail")
    @Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
    BaseResponse getDetailInvetory(InventoryReq req);

    @PostMapping("/inventory/search-list/total-inventory")
    @Headers({ "Accept: application/json; charset=utf-8", "Content-Type: application/x-www-form-urlencoded" })
    HashMap<Integer, Double> getTotalInventory(InventoryReq req);
}
