package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.TlCallBackParam;
import com.armsmart.jupiter.bs.api.service.MemberService;
import com.armsmart.jupiter.bs.api.service.TlCallBackService;
import com.armsmart.jupiter.bs.api.service.TlOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "通联回调接口")
@RequestMapping("/tlCallBack/")
public class TlCallBackController {
    @Autowired(required = false)
    private TlCallBackService tlCallBackService;

    @ApiOperation(value = "电子协议回调函数", notes = "通联电子协议回调接口")
    @ResponseBody
    @RequestMapping(value = "signContractCallBack", method = RequestMethod.POST)
    public CommonResult signContractCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.signContractCallBack(tlCallBackParam);
    }

    @ApiOperation(value = "设置支付密码回调函数", notes = "设置支付密码回调接口")
    @ResponseBody
    @RequestMapping(value = "setPayPwdCallBack", method = RequestMethod.POST)
    public CommonResult setPayPwdCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.setPayPwdCallBack(tlCallBackParam);
    }

    @ApiOperation(value = "托管代收回调函数", notes = "托管代收回调函数")
    @ResponseBody
    @RequestMapping(value = "agentCollectApplyCallBack", method = RequestMethod.POST)
    public CommonResult agentCollectApplyCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.agentCollectApplyCallBack(tlCallBackParam);
    }

    @ApiOperation(value = "订单支付回调", notes = "订单支付回调函数")
    @ResponseBody
    @RequestMapping(value = "orderPayCallBack", method = RequestMethod.POST)
    public CommonResult orderPayCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.orderPayCallBack(tlCallBackParam);
    }

    @ApiOperation(value = "卖家家保证金扣除代付回调", notes = "托管代付回调函数")
    @ResponseBody
    @RequestMapping(value = "sellerAgentPayCallBack", method = RequestMethod.POST)
    public CommonResult sellerAgentPayCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.sellerAgentPayCallBack(tlCallBackParam);
    }

    @ApiOperation(value = "买家保证金扣除代付回调", notes = "托管代付回调函数")
    @ResponseBody
    @RequestMapping(value = "buyerAgentPayCallBack", method = RequestMethod.POST)
    public CommonResult buyerAgentPayCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.buyerAgentPayCallBack(tlCallBackParam);
    }

    @ApiOperation(value = "货款支付回调", notes = "货款支付回调函数")
    @ResponseBody
    @RequestMapping(value = "orderInfoPayCallBack", method = RequestMethod.POST)
    public CommonResult orderInfoPayCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.orderInfoPayCallBack(tlCallBackParam);
    }

    @ApiOperation(value = "提现回调函数", notes = "提现回调函数")
    @ResponseBody
    @RequestMapping(value = "withdrawApplyCallBack", method = RequestMethod.POST)
    public CommonResult withdrawApplyCallBack(TlCallBackParam tlCallBackParam){
        return tlCallBackService.withdrawApplyCallBack(tlCallBackParam);
    }

}
