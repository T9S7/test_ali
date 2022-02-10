package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dao.UserAuthMapper;
import com.armsmart.jupiter.bs.api.dao.UserAuthenticationMapper;
import com.armsmart.jupiter.bs.api.dto.request.*;
import com.armsmart.jupiter.bs.api.dto.response.UserInfoDetail;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.UserAuth;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import com.armsmart.jupiter.bs.api.service.UserAuthService;
import com.armsmart.jupiter.bs.api.service.UserAuthenticationService;
import com.armsmart.jupiter.bs.api.service.UserInfoService;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserInfo 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "用户基本信息接口")
@RequestMapping("/userInfo/")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired(required = false)
    private UserAuthMapper userAuthMapper;

    @Autowired(required = false)
    private UserAuthenticationMapper userAuthenticationMapper;

    @Autowired(required = false)
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("update")
    @ApiOperation("修改用户基础信息（林伟）")
    public CommonResult update(@Validated @RequestBody UserInfoUpdateParam userInfoUpdateParam) {
        userInfoService.update(userInfoUpdateParam);
        return CommonResult.success();
    }

    @PostMapping("setEnable/{userId}")
    @ApiOperation("WEB-启用/禁用（林伟）")
    public CommonResult setEnable(@ApiParam(name = "userId", value = "APP用户ID", required = true) @PathVariable(value = "userId") Integer userId, @ApiParam(name = "isEnable", value = "是否启用", required = true) @RequestParam("isEnable") Boolean isEnable) {
        userInfoService.setIsEnable(userId, isEnable);
        return CommonResult.success();
    }


    @GetMapping("detail/{userId}")
    @ApiOperation("获取用户信息详情（林伟）")
    public CommonResult<UserInfoDetail> detail(@PathVariable("userId") Integer userId) {
        return CommonResult.success(userInfoService.selectById(userId));
    }

    @PostMapping("del/{id}")
    @ApiOperation("WEB-删除（林伟）")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Integer id) {
        userInfoService.deleteById(id);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("WEB-批量删除（林伟）")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Integer> ids) {
        userInfoService.batchDel(ids);
        return CommonResult.success();
    }


    @GetMapping("selectPage")
    @ApiOperation("WEB-分页查询（林伟）")
    public CommonResult<CommonPage<UserInfoDetail>> selectPage(UserInfoQueryParam query) {
        return CommonResult.success(userInfoService.selectPage(query));
    }

    @PostMapping("checkPhoneNum")
    @ApiOperation("确认当前手机号")
    public CommonResult checkPhoneNum(@Validated @RequestBody UserInfoCheckPhoneNumParam userInfoCheckPhoneNumParam) {

        return userInfoService.checkPhoneNum(userInfoCheckPhoneNumParam);
    }

    @PostMapping("changePhoneNum")
    @ApiOperation("APP-变更手机号（林伟）")
    public CommonResult changePhoneNum(@Validated @RequestBody UserInfoChangePhoneNumParam changePhoneNumParam) {
        return userInfoService.changePhoneNum(changePhoneNumParam);
    }

    @PostMapping("accountCancel/{id}")
    @ApiOperation("销户")
    public CommonResult accountCancel(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Integer id) {

        return userInfoService.accountCancel(id);
    }
}
