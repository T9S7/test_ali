package com.armsmart.jupiter.bs.api.controller;

import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.PayCashDetail;
import com.armsmart.jupiter.bs.api.service.TlOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "通联订单接口")
@RequestMapping("/tlOrder/")
public class TlOrderController {
    @Autowired(required = false)
    private TlOrderService tlOrderService;

    @PostMapping("depositApply")
    @ApiOperation("充值申请")
    public CommonResult depositApply(DepositApplyParam param){
        return tlOrderService.depositApply(param);
    }

    @PostMapping("sellerPayCash")
    @ApiOperation("卖家支付保证金")
//    public CommonResult sellerPayCash(@Validated @RequestBody PayCashParam param){
    public CommonResult<PayCashDetail> sellerPayCash(PayCashParam param){
        return tlOrderService.sellerPayCash(param);
    }

    @PostMapping("buyerPayCash")
    @ApiOperation("买家支付保证金")
    public CommonResult<PayCashDetail> buyerPayCash(PayCashParam param){
        return tlOrderService.buyerPayCash(param);
    }

//    @PostMapping("buyerCashReturn")
//    @ApiOperation("买家保证金退还")
//    public CommonResult buyerCashReturn(RefundParam param){
//        return tlOrderService.cashReturn(param);
//    }
//
//    @PostMapping("sellerCashReturn")
//    @ApiOperation("卖家保证金退还")
//    public CommonResult sellerCashReturn(RefundParam param){
//        return tlOrderService.cashReturn(param);
//    }

    @PostMapping("buyerCashDeduct/{orderId}")
    @ApiOperation("买家保证金扣除")
    public CommonResult buyerCashDeduct(@ApiParam(name = "orderId",value = "买家支付保证金订单",required = true) @PathVariable(value = "orderId")String orderId){
        return tlOrderService.buyerCashDeduct(orderId);
    }

    @PostMapping("sellerCashDeduct/{orderId}")
    @ApiOperation("卖家保证金扣除")
    public CommonResult sellerCashDeduct(@ApiParam(name = "orderId",value = "卖家支付保证金订单",required = true) @PathVariable(value = "orderId")String orderId){
        return tlOrderService.sellerCashDeduct(orderId);
    }

    @PostMapping("withdrawApply")
    @ApiOperation("提现申请")
    public CommonResult withdrawApply(WithdrawApplyParam param){
        return tlOrderService.withdrawApply(param);
    }

    @PostMapping("payByBackSMS")
    @ApiOperation("确认支付（后台+短信验证码）")
    public CommonResult payByBackSMS(PayByBackSMSParam param){
        return tlOrderService.payByBackSMS(param);
    }

    @PostMapping("refund")
    @ApiOperation("退款申请")
    public CommonResult refund(RefundParam param){
        return tlOrderService.refund(param);
    }

    @PostMapping("getOrderDetail/{orderId}")
    @ApiOperation("查询订单状态（1-未支付，3-交易失败，4-交易成功，5-交易成功-发生退款，6-关闭，99-进行中）")
    public CommonResult getOrderDetail(@ApiParam(name = "orderId",value = "订单编号",required = true) @PathVariable(value = "orderId")String orderId){
        return tlOrderService.getOrderDetail(orderId);
    }

    @PostMapping("queryInExpDetail")
    @ApiOperation("查询账户收支明细")
    public CommonResult queryInExpDetail(QueryInExpDetailParam param){
        return tlOrderService.queryInExpDetail(param);
    }

    @PostMapping("queryBalance/{userId}")
    @ApiOperation("查询账户余额")
    public CommonResult queryBalance(@ApiParam(name = "userId",value = "订单编号",required = true) @PathVariable(value = "userId")String userId){
        return tlOrderService.queryBalance(userId);
    }

//    @PostMapping("queryVSPFund")
//    @ApiOperation("查询账户收支明细")
//    public CommonResult queryVSPFund(QueryVSPFundParam param){
//        return tlOrderService.queryVSPFund(param);
//    }

    @PostMapping("applicationTransfer")
    @ApiOperation("平台转账")
    public CommonResult applicationTransfer(ApplicationTransferParam param){
        return tlOrderService.applicationTransfer(param);
    }

    //123
}
