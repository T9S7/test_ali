package com.armsmart.jupiter.bs.api.controller;

import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoParam;
import com.armsmart.jupiter.bs.api.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wei.lin
 * @date 2021/8/18
 */
@RestController
@Api(tags = "微信支付接口")
@RequestMapping("/wxpay/")
public class PayController {


    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("callback")
    @ApiOperation("微信支付回调接口（张荣）")
    public String payCallback(HttpServletRequest request) throws Exception {
        return orderInfoService.payCallback(request);
    }

    @PostMapping("orderQuery")
    @ApiOperation("微信订单查询接口（张荣）")
    public CommonResult orderQuery(@Validated @RequestBody WechatOrderInfoParam wechatOrderInfoParam) throws Exception {
        return orderInfoService.weChatOrderquery(wechatOrderInfoParam);
    }
}
