package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.SysUserDetail;
import com.armsmart.jupiter.bs.api.dto.response.SysUserLoginResult;
import com.armsmart.jupiter.bs.api.entity.SmsInfo;
import com.armsmart.jupiter.bs.api.service.SmsInfoService;
import com.armsmart.jupiter.bs.api.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysUser 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "系统用户接口")
@RequestMapping("/sysUser/")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SmsInfoService smsInfoService;

    @PostMapping("add")
    @ApiOperation("添加")
    public CommonResult add(@Validated @RequestBody SysUserAddParam sysUserAddParam) {
        return CommonResult.success(sysUserService.insert(sysUserAddParam));
    }

    @PostMapping("update")
    @ApiOperation("修改")
    public CommonResult update(@Validated @RequestBody SysUserUpdateParam sysUserUpdateParam) {
        sysUserService.update(sysUserUpdateParam);
        return CommonResult.success();
    }

    @PostMapping("setEnable/{userId}")
    @ApiOperation("启用/禁用")
    public CommonResult setEnable(@ApiParam(name = "userId", value = "主键ID", required = true) @PathVariable(value = "userId") Integer userId, @ApiParam(name = "isEnable", value = "是否启用", required = true) @RequestParam("isEnable") Boolean isEnable) {
        sysUserService.setIsEnable(userId, isEnable);
        return CommonResult.success();
    }

    @PostMapping("changePwd")
    @ApiOperation("修改密码")
    public CommonResult changePwd(@Validated @RequestBody SysUserChangePwdParam changePwdParam) {
        sysUserService.changePwd(changePwdParam);
        return CommonResult.success();
    }


    @PostMapping("del/{userId}")
    @ApiOperation("删除")
    public CommonResult delete(@ApiParam(name = "userId", value = "主键ID", required = true) @PathVariable(value = "userId") Integer userId) {
        sysUserService.deleteById(userId);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("批量删除")
    public CommonResult batchDel(@ApiParam(name = "userIds", value = "主键ID集合", required = true) @RequestParam(value = "userIds") List<Integer> userIds) {
        sysUserService.batchDel(userIds);
        return CommonResult.success();
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<SysUserDetail>> selectPage(SysUserQueryParam query) {
        return CommonResult.success(sysUserService.selectPage(query));
    }

    @GetMapping("detail/{userId}")
    @ApiOperation("用户详情")
    public CommonResult<SysUserDetail> selectPage(@ApiParam(name = "userId", value = "主键ID", required = true) @PathVariable(value = "userId") Integer userId) {
        return CommonResult.success(sysUserService.selectById(userId));
    }

    @ApiOperation(value = "用户登录-密码")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<SysUserLoginResult> login(@Validated @RequestBody SysUserLoginParam sysUserLoginParam) {
        return CommonResult.success(sysUserService.login(sysUserLoginParam));
    }

    @ApiOperation(value = "用户登录-验证码")
    @RequestMapping(value = "/verifyCodeLogin", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<SysUserLoginResult> login(@Validated @RequestBody SysUserVerifyCodeLoginParam loginParam) {
        return CommonResult.success(sysUserService.login(loginParam));
    }

    @GetMapping("getAuthVerifyCode")
    @ApiOperation("获取短信验登录证码")
    public CommonResult getAuthVerifyCode(@ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam("mobile") String mobile) {
        smsInfoService.sendVerifyCode(mobile, SmsInfo.SmsType.AUTH);
        return CommonResult.success();
    }
}

