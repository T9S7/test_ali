package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.ApplicationTransferParam;
import com.armsmart.jupiter.bs.api.dto.request.QueryBankBalanceParam;
import com.armsmart.jupiter.bs.api.dto.request.QueryVSPFundParam;
import com.armsmart.jupiter.bs.api.service.MerchantSerService;
import com.armsmart.jupiter.bs.api.tlpay.MemberApiTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "通联辅助接口")
@RequestMapping("/merchantSer/")
public class MerchantSerController {

    @Autowired(required = false)
    private MerchantSerService merchantSerService;

    @PostMapping("queryVSPFund")
    @ApiOperation("收银宝商户资金查询 ")
    public CommonResult queryVSPFund(QueryVSPFundParam param){
        return merchantSerService.queryVSPFund(param);
    }

    @GetMapping("queryReserveFundBalance")
    @ApiOperation("平台头寸查询 ")
    public CommonResult queryReserveFundBalance(){
        return merchantSerService.queryReserveFundBalance();
    }

    @PostMapping("queryBankBalance")
    @ApiOperation("平台银行存管账户余额查询 ")
    public CommonResult queryBankBalance(QueryBankBalanceParam param){
        return merchantSerService.queryBankBalance(param);
    }

    @PostMapping("queryMerchantBalance/{accountSetNo}")
    @ApiOperation("平台账户集余额查询 ")
    public CommonResult queryMerchantBalance(@ApiParam(name = "accountSetNo",value = "账户集编号",required = true) @PathVariable(value = "accountSetNo")String accountSetNo){
        return merchantSerService.queryMerchantBalance(accountSetNo);
    }

    @PostMapping("eleReceiptDownload/{orderId}")
    @ApiOperation("电子回单下载--提现完成后才有")
    public CommonResult eleReceiptDownload(@ApiParam(name = "orderId",value = "账户集编号",required = true) @PathVariable(value = "orderId")String orderId){
        return merchantSerService.eleReceiptDownload(orderId);
    }

    //待退款列表
    //当日流水


}
