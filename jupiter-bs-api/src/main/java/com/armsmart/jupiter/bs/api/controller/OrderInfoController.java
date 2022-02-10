package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.NfcOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.OrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.PayCashDetail;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import com.armsmart.jupiter.bs.api.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderInfo 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "订单管理接口")
@RequestMapping("/orderInfo/")
public class OrderInfoController extends BaseController {

    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("add")
    @ApiOperation("APP-添加（林伟）")
    public CommonResult<OrderInfoDetail> add(@Validated @RequestBody OrderInfoAddParam orderInfoAddParam) throws Exception {
        return orderInfoService.insert(orderInfoAddParam);
    }

    @PostMapping("orderPay")
    @ApiOperation("APP-支付（储栋）")
    public CommonResult<PayCashDetail> orderPay(@Validated @RequestBody OrderPayParam orderPayParam)  {
        return orderInfoService.orderPay(orderPayParam);
    }

    @PostMapping("orderPayAgain")
    @ApiOperation("APP-未支付订单再次支付（储栋）")
    public CommonResult<PayCashDetail> orderPayAgain(@Validated @RequestBody OrderPayParam orderPayParam)  {
        return orderInfoService.orderPayAgain(orderPayParam);
    }

    @PostMapping("update")
    @ApiOperation("WEB-修改（林伟）")
    public CommonResult update(@Validated @RequestBody OrderInfoUpdateParam orderInfoUpdateParam) {
        return orderInfoService.update(orderInfoUpdateParam);
    }

    @PostMapping("del/{orderId}")
    @ApiOperation("APP-单条删除（林伟）")
    public CommonResult delete(@ApiParam(name = "orderId", value = "主键ID", required = true) @PathVariable(value = "orderId") Long orderId) {
        return orderInfoService.deleteById(orderId, getCurrentUserId());
    }

    @PostMapping("batchDel")
    @ApiOperation("WEB-批量删除（林伟）")
    public CommonResult batchDel(@ApiParam(name = "orderIds", value = "主键ID集合", required = true) @RequestParam(value = "orderIds") List<Long> orderIds) {
        return orderInfoService.batchDel(orderIds);
    }

    @GetMapping("selectPage")
    @ApiOperation("WEB-分页查询订单（林伟）")
    public CommonResult<CommonPage<OrderInfoDetail>> selectPage(OrderInfoQueryParam query) {
        return CommonResult.success(orderInfoService.selectPage(query));
    }

    @GetMapping("selectById/{orderId}")
    @ApiOperation("查询订单详情（林伟）")
    public CommonResult<OrderInfoDetail> selectById(@ApiParam(name = "orderId", value = "主键ID", required = true) @PathVariable(value = "orderId") Long orderId) {
        return orderInfoService.selectById(orderId);
    }

    @GetMapping("getNfcOrder")
    @ApiOperation("WEB-芯片读卡器订单信息")
    public CommonResult<CommonPage<NfcOrderInfoDetail>> getNfcOrder(@Validated PageQueryParam param) {
        return CommonResult.success(orderInfoService.getNfcOrder(param));
    }


    @PostMapping("sendOut")
    @ApiOperation("APP-订单发货（林伟）")
    public CommonResult sendOut(@Validated @RequestBody OrderInfoSendOutParam sendOutParam) {
        return orderInfoService.sendOut(sendOutParam);
    }


    @PostMapping("thingSendOut")
    @ApiOperation("APP发货收货链上操作（储栋）")
    public DeferredResult<CommonResult> thingSendOut(@Validated @RequestBody ModifyOwnerAddInfoParam param) {
        return orderInfoService.thingSendOut(param);
    }

    @PostMapping("getUnifiedOrderInfo")
    @ApiOperation("获取微信预支付返回的信息(张荣)")
    public CommonResult getUnifiedOrderInfo(@Validated @RequestBody OrderInfoQueryParam orderInfoQueryParam, HttpServletRequest request) throws Exception {
        return orderInfoService.getUnifiedOrderInfo(orderInfoQueryParam, request);
    }
}

