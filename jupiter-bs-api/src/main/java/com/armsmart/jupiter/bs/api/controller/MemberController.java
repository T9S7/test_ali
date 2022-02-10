package com.armsmart.jupiter.bs.api.controller;

import com.alibaba.fastjson.JSON;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.service.MemberService;
import com.armsmart.jupiter.bs.api.tlpay.MemberApiTest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Api(tags = "通联会员接口")
@RequestMapping("/member/")
public class MemberController {

    @Autowired(required = false)
    private MemberService memberService;

    @Autowired(required = false)
    private MemberApiTest memberApiTest;

    @PostMapping("add/{userId}")
    @ApiOperation("添加")
    public CommonResult add(@ApiParam(name = "userId",value = "爱较真用户ID",required = true) @PathVariable(value = "userId") String userId)throws Exception {
        return memberService.add(userId);
    }

    @PostMapping("sendVerificationCode")
    @ApiOperation("发送短信验证码")
    public CommonResult sendVerificationCode(@Validated @RequestBody SendVerificationCodeParam sendVerificationCodeParam) {
        return memberService.sendVerificationCode(sendVerificationCodeParam);
    }

    @PostMapping("bindPhone")
    @ApiOperation("绑定手机号")
    public CommonResult bindPhone(@Validated @RequestBody UnbindPhoneParam bindPhoneParam){
        return memberService.bindPhone(bindPhoneParam);
    }

    @PostMapping("onlySetRealName")
    @ApiOperation("实名认证")
    public CommonResult onlySetRealName(@Validated @RequestBody OnlySetRealNameParam param){
        return memberService.onlySetRealName(param);
    }

    @PostMapping("setRealName")
    @ApiOperation("实名认证并绑定手机号")
    public CommonResult setRealName(@Validated @RequestBody SetRealNameParam setRealNameParam) {
        return memberService.setRealName(setRealNameParam);
    }

    @PostMapping("signContract/{userId}")
    @ApiOperation("电子协议签订")
    public CommonResult signContract(@ApiParam(name = "userId",value = "爱较真用户ID",required = true) @PathVariable(value = "userId") String userId)throws Exception {
        return memberService.signContract(userId);
    }

    @PostMapping("getMemberInfo/{userId}")
    @ApiOperation("获取会员详情")
    public CommonResult getMemberInfo(@ApiParam(name = "userId",value = "爱较真用户ID",required = true) @PathVariable(value = "userId") String userId){
        return memberService.getMemberInfo(userId);
    }

    @PostMapping("getBankCardBin/{cardNo}")
    @ApiOperation("查询卡bin")
    public CommonResult getBankCardBin(@ApiParam(name = "cardNo",value = "银行卡号",required = true) @PathVariable(value = "cardNo") String cardNo){
        return memberService.getBankCardBin(cardNo);
    }

    @PostMapping("applyBindBankCard")
    @ApiOperation("请求绑定银行卡")
    public CommonResult applyBindBankCard(@Validated @RequestBody ApplyBindBankCardParam param){
        return memberService.applyBindBankCard(param);
    }

    @PostMapping("bindBankCard")
    @ApiOperation("确认绑卡")
    public CommonResult bindBankCard(@Validated @RequestBody BindBankCardParam param){
        return memberService.bindBankCard(param);
    }

    @PostMapping("queryBankCard")
    @ApiOperation("查询绑定银行卡")
    public CommonResult queryBankCard(@Validated @RequestBody QueryBankCardParam param){
        return memberService.queryBankCard(param);
    }

    @PostMapping("unbindBankCard")
    @ApiOperation("银行卡解绑")
    public CommonResult unbindBankCard(@Validated @RequestBody UnbindBankCardParam param){
        return memberService.unbindBankCard(param);
    }

    @PostMapping("setPayPwd")
    @ApiOperation("设置支付密码")
    public CommonResult setPayPwd(@Validated @RequestBody SetPayPwdParam param){
        return memberService.setPayPwd(param);
    }

    @PostMapping("updatePayPwd")
    @ApiOperation("修改支付密码")
    public CommonResult updatePayPwd(@Validated @RequestBody UpdatePayPwdParam param){
        return memberService.updatePayPwd(param);
    }

    @PostMapping("resetPayPwd")
    @ApiOperation("重置支付密码")
    public CommonResult resetPayPwd(@Validated @RequestBody ResetPayPwdParam param){
        return memberService.resetPayPwd(param);

    }

    @PostMapping("updatePhoneByPayPwd")
    @ApiOperation("修改绑定手机号（密码验证版）")
    public CommonResult updatePhoneByPayPwd(@Validated @RequestBody UpdatePhoneByPayPwdParam param){
        return memberService.updatePhoneByPayPwd(param);
    }

    @PostMapping("unbindPhone")
    @ApiOperation("解绑手机号（原手机号短信验证）")
    public CommonResult unbindPhone(@Validated @RequestBody UnbindPhoneParam param){
        return memberService.unbindPhone(param);
    }

}
