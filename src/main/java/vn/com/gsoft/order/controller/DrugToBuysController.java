package vn.com.gsoft.order.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.gsoft.order.constant.PathContains;
import vn.com.gsoft.order.model.dto.DrugToBuysReq;
import vn.com.gsoft.order.model.system.BaseResponse;
import vn.com.gsoft.order.service.DrugToBuysService;
import vn.com.gsoft.order.util.system.ResponseUtils;


@Slf4j
@RestController
@RequestMapping("/drug-to-buys")
public class DrugToBuysController {
	
  @Autowired
  DrugToBuysService service;


  @PostMapping(value = PathContains.URL_SEARCH_PAGE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colection(@RequestBody DrugToBuysReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchPage(objReq)));
  }

  @PostMapping(value = "/cancel-drug-buy", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> cancelDrugBuy(@RequestBody DrugToBuysReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.cancelDrugBuy(objReq)));
  }

  @PostMapping(value = "/restore-drug-buy", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> restoreDrugBuy(@RequestBody DrugToBuysReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.restoreDrugBuy(objReq)));
  }

  @PostMapping(value = "/complete-drug-buy", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> completeDrugToBuy(@RequestBody DrugToBuysReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.completeDrugToBuy(objReq)));
  }


  @PostMapping(value = PathContains.URL_SEARCH_LIST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> colectionList(@RequestBody DrugToBuysReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.searchList(objReq)));
  }


  @PostMapping(value = PathContains.URL_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> insert(@Valid @RequestBody DrugToBuysReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.create(objReq)));
  }


  @PostMapping(value = PathContains.URL_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<BaseResponse> update(@Valid @RequestBody DrugToBuysReq objReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.update(objReq)));
  }


  @GetMapping(value = PathContains.URL_DETAIL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> detail(@PathVariable("id") Long id) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.detail(id)));
  }


  @PostMapping(value = PathContains.URL_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<BaseResponse> delete(@Valid @RequestBody DrugToBuysReq idSearchReq) throws Exception {
    return ResponseEntity.ok(ResponseUtils.ok(service.delete(idSearchReq.getId())));
  }
}
