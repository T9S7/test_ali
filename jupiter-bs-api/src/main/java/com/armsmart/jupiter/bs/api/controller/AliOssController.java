package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.config.OssConfig;
import com.armsmart.jupiter.bs.api.dto.response.AssumeRoleResult;
import com.armsmart.jupiter.bs.api.service.AliOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "对象存储OSS接口")
@RequestMapping("/oss/")
public class AliOssController {

    @Autowired
    private AliOssService aliOssService;

    @Autowired
    private OssConfig ossConfig;

    @ApiOperation("e2prove资源上传公用接口")
    @GetMapping("e2proveBucketAssumeRole")
    public CommonResult<AssumeRoleResult> e2proveBucketAssumeRole() {
        return CommonResult.success(aliOssService.getAcsResponse(ossConfig.getAvatarBucketName()));
    }

    @ApiOperation("app安装包上传授权接口")
    @GetMapping("appPackageBucketAssumeRole")
    public CommonResult<AssumeRoleResult> appBucketAssumeRole() {
        return CommonResult.success(aliOssService.getAcsResponse(ossConfig.getAppPackageBucketName()));
    }

}
