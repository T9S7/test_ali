package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.AppTutorialVideoDetail;
import com.armsmart.jupiter.bs.api.service.AppTutorialVideoService;
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
 * AppTutorialVideo 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "教程视频管理接口")
@RequestMapping("/appTutorialVideo/")
public class AppTutorialVideoController {

    @Autowired
    private AppTutorialVideoService appTutorialVideoService;

    @PostMapping("add")
    @ApiOperation("添加")
    public CommonResult add(@Validated @RequestBody AppTutorialVideoAddParam appTutorialVideoAddParam) {
        return appTutorialVideoService.insert(appTutorialVideoAddParam);
    }

    @PostMapping("update")
    @ApiOperation("修改")
    public CommonResult update(@Validated @RequestBody AppTutorialVideoUpdateParam appTutorialVideoUpdateParam) {

        return appTutorialVideoService.update(appTutorialVideoUpdateParam);
    }

    @PostMapping("del/{id}")
    @ApiOperation("删除")
    public CommonResult delete(@ApiParam(name = "id", value = "主键ID", required = true) @PathVariable(value = "id") Integer id) {
        appTutorialVideoService.deleteById(id);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("批量删除")
    public CommonResult batchDel(@ApiParam(name = "ids", value = "主键ID集合", required = true) @RequestParam(value = "ids") List<Integer> ids) {
        appTutorialVideoService.batchDel(ids);
        return CommonResult.success();
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<AppTutorialVideoDetail>> selectPage(AppTutorialVideoQueryParam query) {
        PageInfo<AppTutorialVideoDetail> pageInfo = appTutorialVideoService.selectPage(query);
        return CommonResult.success(CommonPage.restPage(pageInfo));
    }

}

