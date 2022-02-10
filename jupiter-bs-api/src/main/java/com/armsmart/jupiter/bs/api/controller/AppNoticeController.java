package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AppNoticeDetail;
import com.armsmart.jupiter.bs.api.service.AppNoticeService;
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

/**
 * AppNotice 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "App通知接口（WEB端）")
@RequestMapping("/appNotice/")
public class AppNoticeController {

    @Autowired
    private AppNoticeService appNoticeService;

    @PostMapping("add")
    @ApiOperation("添加")
    public CommonResult add(@Validated @RequestBody AppNoticeAddParam appNoticeAddParam) {
        return CommonResult.success(appNoticeService.insert(appNoticeAddParam));
    }

    @PostMapping("update")
    @ApiOperation("修改")
    public CommonResult update(@Validated @RequestBody AppNoticeUpdateParam appNoticeUpdateParam) {
        appNoticeService.update(appNoticeUpdateParam);
        return CommonResult.success();
    }

    @PostMapping("del/{id}")
    @ApiOperation("删除")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Integer id) {
        appNoticeService.deleteById(id);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("批量删除")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Integer> ids) {
        appNoticeService.batchDel(ids);
        return CommonResult.success();
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<AppNoticeDetail>> selectPage(AppNoticeQueryParam query) {
        PageInfo<AppNoticeDetail> pageInfo = appNoticeService.selectPage(query);
        return CommonResult.success(CommonPage.restPage(pageInfo));
    }
}

