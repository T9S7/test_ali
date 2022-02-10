package com.armsmart.jupiter.bs.api.controller.app;

import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.response.AppNoticeDetail;
import com.armsmart.jupiter.bs.api.dto.response.AppVersionDetail;
import com.armsmart.jupiter.bs.api.service.AppNoticeService;
import com.armsmart.jupiter.bs.api.service.AppVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wei.lin
 * @date 2021/12/27
 */

@Api(tags = "APP-启动页接口")
@RestController
@RequestMapping("/launch/")
public class LaunchController {

    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private AppNoticeService appNoticeService;

    @GetMapping("getVersion/{platform}")
    @ApiOperation("获取最新版本信息")
    public CommonResult<AppVersionDetail> getVersion(
            @Validated
            @ApiParam(name = "platform", value = "系统 0 安卓，1 ios", required = true)
            @PathVariable(value = "platform") Integer platform) {
        return appVersionService.getLatestVersion(platform);
    }

    @GetMapping("getMaintenanceNotice")
    @ApiOperation("获取维护通知信息")
    public CommonResult<AppNoticeDetail> getMaintenanceNotice() {
        return CommonResult.success(appNoticeService.getMaintenanceNotice());
    }

}
