package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.constants.ResourceType;
import com.armsmart.jupiter.bs.api.assembler.SysResourceAssembler;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.entity.SysResource;
import com.armsmart.jupiter.bs.api.service.SysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SysResource 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "系统资源接口")
@RequestMapping("/sysResource/")
public class SysResourceController {

    @Autowired
    private SysResourceService sysResourceService;

    @PostMapping("add")
    @ApiOperation("添加")
    public CommonResult add(@Validated @RequestBody SysResourceAddParam sysResourceAddParam) {
        return CommonResult.success(sysResourceService.insert(sysResourceAddParam));
    }

    @PostMapping("update")
    @ApiOperation("修改")
    public CommonResult update(@Validated @RequestBody SysResourceUpdateParam sysResourceUpdateParam) {
        sysResourceService.update(sysResourceUpdateParam);
        return CommonResult.success();
    }

    @PostMapping("enable/{resourceId}")
    @ApiOperation("启用")
    public CommonResult enable(@ApiParam(name = "resourceId", value = "主键ID", required = true) @PathVariable(value = "resourceId") Integer resourceId) {
        sysResourceService.setIsEnable(resourceId, true);
        return CommonResult.success();
    }

    @PostMapping("disable/{resourceId}")
    @ApiOperation("禁用")
    public CommonResult disable(@ApiParam(name = "resourceId", value = "主键ID", required = true) @PathVariable(value = "resourceId") Integer resourceId) {
        sysResourceService.setIsEnable(resourceId, false);
        return CommonResult.success();
    }

    @PostMapping("del/{resourceId}")
    @ApiOperation("删除")
    public CommonResult delete(@ApiParam(name = "resourceId", value = "主键ID", required = true) @PathVariable(value = "resourceId") Integer resourceId) {
        sysResourceService.deleteById(resourceId);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("批量删除")
    public CommonResult batchDel(@ApiParam(name = "resourceIds", value = "主键ID集合", required = true) @RequestParam(value = "resourceIds") List<Integer> resourceIds) {
        sysResourceService.batchDel(resourceIds);
        return CommonResult.success();
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<SysResourceDetail>> selectPage(SysResourceQueryParam query) {
        return CommonResult.success(sysResourceService.selectPage(query));
    }

    @GetMapping("selectTree")
    @ApiOperation(value = "查询所有资源-树结构返回", position = 10)
    public CommonResult<List<SysResourceDetail>> selectTree() {
        List<SysResourceDetail> list = sysResourceService.selectTree();
        return CommonResult.success(list);
    }

    @GetMapping("list")
    @ApiOperation(value = "查询菜单资源-平铺结构返回", position = 10)
    public CommonResult<List<SysResourceDetail>> list(@RequestParam(value = "containsBtn", defaultValue = "false", required = false) Boolean containsBtn) {
        List<SysResource> list = sysResourceService.selectAll();
        if (!CollectionUtils.isEmpty(list)) {
            List<SysResourceDetail> resultList = SysResourceAssembler.INSTANCE.getSysResourceDetailList(list.stream().filter(item -> {
                if (!containsBtn) {
                    return item.getResourceType() == ResourceType.MENU.getCode();
                }
                return true;
            }).collect(Collectors.toList()));
            return CommonResult.success(resultList);
        }
        return CommonResult.success(Collections.emptyList());

    }
}

