package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.MobileVerifyLoginParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthAdminAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthChangePhoneNumParam;
import com.armsmart.jupiter.bs.api.dto.request.UserMobileLoginParam;
import com.armsmart.jupiter.bs.api.dto.request.UserModifyPwdParam;
import com.armsmart.jupiter.bs.api.dto.request.UserResetPwdParam;
import com.armsmart.jupiter.bs.api.dto.request.UserVerifyCodeLoginParam;
import com.armsmart.jupiter.bs.api.dto.response.CheckTokenResult;
import com.armsmart.jupiter.bs.api.dto.response.UserLoginResult;
import com.armsmart.jupiter.bs.api.entity.SmsInfo;
import com.armsmart.jupiter.bs.api.service.MobileVerifyService;
import com.armsmart.jupiter.bs.api.service.SmsInfoService;
import com.armsmart.jupiter.bs.api.service.UserAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * UserAuth 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "用户授权接口")
@RequestMapping("/userAuth/")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private SmsInfoService smsInfoService;
    @Autowired
    private MobileVerifyService mobileVerifyService;

    @PostMapping("register")
    @ApiOperation(value = "用户注册", hidden = true)
    public CommonResult register(@Validated @RequestBody UserAuthAddParam userAuthAddParam) {
        return CommonResult.success(userAuthService.insert(userAuthAddParam));
    }

    @PostMapping("admin/register")
    @ApiOperation(value = "用户注册-管理端", hidden = true)
    public CommonResult register(@Validated @RequestBody UserAuthAdminAddParam userAuthAddParam) {
        return CommonResult.success(userAuthService.insert(userAuthAddParam));
    }


    @GetMapping("verifyCode")
    @ApiOperation("获取注册/登录短信验证码")
    public CommonResult verifyCode(@ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam("mobile") String mobile) {
        smsInfoService.sendVerifyCode(mobile, SmsInfo.SmsType.REG_LOGIN);
        return CommonResult.success();
    }

    @GetMapping("getAuthVerifyCode")
    @ApiOperation("获取身份认证短信验证码")
    public CommonResult getAuthVerifyCode(@ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam("mobile") String mobile) {
        smsInfoService.sendVerifyCode(mobile, SmsInfo.SmsType.AUTH);
        return CommonResult.success();
    }

    @GetMapping("checkAuthVerifyCode")
    @ApiOperation("校验身份认证短信验证码")
    public CommonResult checkAuthVerifyCode(@ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam("mobile") String mobile,
                                            @ApiParam(name = "verifyCode", value = "验证码", required = true) @RequestParam("verifyCode") String verifyCode) {
        smsInfoService.checkVerifyCode(mobile, SmsInfo.SmsType.AUTH, verifyCode);
        return CommonResult.success();
    }

    @ApiOperation(value = "用户登录-密码", hidden = true)
    @RequestMapping(value = "/mobileLogin", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UserLoginResult> mobileLogin(@Validated @RequestBody UserMobileLoginParam userMobileLoginParam) {
        return CommonResult.success(userAuthService.login(userMobileLoginParam));
    }

    @ApiOperation(value = "用户登录-验证码")
    @RequestMapping(value = "/verifyCodeLogin", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UserLoginResult> verifyCodeLogin(@Validated @RequestBody UserVerifyCodeLoginParam param) {
        return CommonResult.success(userAuthService.login(param));
    }

    @ApiOperation(value = "用户修改密码")
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult modifyPwd(@Validated @RequestBody UserModifyPwdParam userModifyPwdParam) {
        userAuthService.modifyPwd(userModifyPwdParam);
        return CommonResult.success();
    }

    @ApiOperation(value = "用户重置密码")
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult mobileLogin(@Validated @RequestBody UserResetPwdParam userResetPwdParam) {
        userAuthService.resetPwd(userResetPwdParam);
        return CommonResult.success();
    }

    @ApiOperation(value = "号码认证-登录")
    @RequestMapping(value = "/mobileVerifyLogin", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UserLoginResult> mobileVerifyLogin(@Validated @RequestBody MobileVerifyLoginParam param) throws Exception {
        return CommonResult.success(userAuthService.login(param));
    }

    @ApiOperation(value = "校验Token")
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<CheckTokenResult> checkToken(HttpServletRequest request) {
        return CommonResult.success(userAuthService.checkToken(request));
    }
}

