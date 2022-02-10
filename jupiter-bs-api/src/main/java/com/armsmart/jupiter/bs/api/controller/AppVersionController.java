package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AppVersionDetail;
import com.armsmart.jupiter.bs.api.service.AppVersionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import com.armsmart.jupiter.bs.api.dto.request.AppVersionUpdateParam;
//import com.armsmart.jupiter.bs.api.dto.request.AppVersionQueryParam;

/**
 * AppVersion 接口
 *
 * @author 苏礼刚
 **/
@RestController
@Api(tags = "App版本接口（WEB端）")
@RequestMapping("/appVersion/")
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<AppVersionDetail>> selectPage(AppVersionQueryParam query) {
        PageInfo<AppVersionDetail> pageInfo = appVersionService.selectPage(query);
        return CommonResult.success(CommonPage.restPage(pageInfo));
    }

    @PostMapping("add")
    @ApiOperation("添加")
    public CommonResult add(@Validated @RequestBody AppVersionAddParam appVersionAddParam) {
        return appVersionService.insert(appVersionAddParam);
    }

    @PostMapping("update")
    @ApiOperation("修改")
    public CommonResult update(@Validated @RequestBody AppVersionUpdateParam appVersionUpdateParam) {
        appVersionService.update(appVersionUpdateParam);
        return CommonResult.success();
    }

    @PostMapping("del/{id}")
    @ApiOperation("删除")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") java.lang.Integer id) {
        appVersionService.deleteById(id);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("批量删除")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Integer> ids) {
        appVersionService.batchDel(ids);
        return CommonResult.success();
    }


    @PostMapping("getVersion/{platform}")
    @ApiOperation("获取最新版本信息")
    public CommonResult<AppVersionDetail> getVersion(@ApiParam(name = "platform", value = "系统 0 安卓，1 ios", required = true) @PathVariable(value = "platform") Integer platform) {
        return appVersionService.getLatestVersion(platform);
    }
}

