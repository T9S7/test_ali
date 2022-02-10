package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.AddOrderParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatMiniPayParam;
import com.armsmart.jupiter.bs.api.dto.response.MiniPayDetail;
import com.armsmart.jupiter.bs.api.service.WeChatMiniPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "微信小程序支付接口")
@RequestMapping("/wechatMini/")
public class WechatMiniPayController {

    @Autowired
    private WeChatMiniPayService weChatMiniPayService;

    @PostMapping("pay")
    @ApiOperation("支付")
    public CommonResult pay(@Validated @RequestBody WechatMiniPayParam wechatMiniPayParam) {
        return weChatMiniPayService.pay(wechatMiniPayParam);
    }

//    @PostMapping("postOpenId")
//    @ApiOperation("支付")
//    public CommonResult postOpenId( String code) {
//        return weChatMiniPayService.postOpenId(code);
//    }

    @PostMapping("addOrder")
    @ApiOperation("微信生成订单")
    public CommonResult<MiniPayDetail> addOrder(@Validated @RequestBody AddOrderParam addOrderParam) {
        return weChatMiniPayService.addOrder(addOrderParam);
    }

}
