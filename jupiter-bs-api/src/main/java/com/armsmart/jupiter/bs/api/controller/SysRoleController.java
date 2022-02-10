package com.armsmart.jupiter.bs.api.controller;


import com.armsmart.jupiter.bs.api.dto.request.SysRoleAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysRoleDetail;
import com.armsmart.common.api.CommonPage;
import com.armsmart.common.api.CommonResult;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleGrantParam;
import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SysRole 接口
 *
 * @author wei.lin
 **/
@RestController
@Api(tags = "系统角色接口")
@RequestMapping("/sysRole/")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("add")
    @ApiOperation("添加")
    public CommonResult add(@Validated @RequestBody SysRoleAddParam sysRoleAddParam) {
        return CommonResult.success(sysRoleService.insert(sysRoleAddParam));
    }

    @PostMapping("update")
    @ApiOperation("修改")
    public CommonResult update(@Validated @RequestBody SysRoleUpdateParam sysRoleUpdateParam) {
        sysRoleService.update(sysRoleUpdateParam);
        return CommonResult.success();
    }

    @PostMapping("del/{roleId}")
    @ApiOperation("删除")
    public CommonResult delete(@ApiParam(name = "roleId", value = "主键ID", required = true) @PathVariable(value = "roleId") Integer roleId) {
        sysRoleService.deleteById(roleId);
        return CommonResult.success();
    }

    @PostMapping("batchDel")
    @ApiOperation("批量删除")
    public CommonResult batchDel(@ApiParam(name = "roleIds", value = "主键ID集合", required = true) @RequestParam(value = "roleIds") List<Integer> roleIds) {
        sysRoleService.batchDel(roleIds);
        return CommonResult.success();
    }

    @GetMapping("selectPage")
    @ApiOperation("分页查询")
    public CommonResult<CommonPage<SysRoleDetail>> selectPage(SysRoleQueryParam query) {
        return CommonResult.success(sysRoleService.selectPage(query));
    }

    @GetMapping("listAll")
    @ApiOperation("查询所有")
    public CommonResult<List<SysRoleDetail>> listAll() {
        return CommonResult.success(sysRoleService.selectAll());
    }

    @GetMapping("/resourceTree/{roleId}")
    @ApiOperation(value = "根据角色ID查询资源权限-树结构", position = 10)
    public CommonResult<List<SysResourceDetail>> selectRoleResourceTree(@ApiParam(name = "roleId", value = "角色ID", required = true) @PathVariable(value = "roleId") Integer roleId, @ApiParam(name = "containsBtn", value = "是否包含按钮", required = true) @RequestParam Boolean containsBtn) {
        return CommonResult.success(sysRoleService.selectRolePermissionTree(roleId, containsBtn));
    }

    @GetMapping("/resourceList/{roleId}")
    @ApiOperation(value = "根据角色ID查询资源权限-平铺结构", position = 10)
    public CommonResult<List<SysResourceDetail>> selectRolePermissionList(@ApiParam(name = "roleId", value = "角色ID", required = true) @PathVariable(value = "roleId") Integer roleId) {
        return CommonResult.success(sysRoleService.selectRolePermission(roleId));
    }

    @GetMapping("/btnResourceList/{roleId}")
    @ApiOperation(value = "根据角色ID查询按钮权限-平铺", position = 12)
    public CommonResult<List<SysResourceDetail>> selectBtnResourceList(@ApiParam(name = "roleId", value = "角色ID", required = true) @PathVariable(value = "roleId") Integer roleId) {
        return CommonResult.success(sysRoleService.selectRoleBtnPermission(roleId));
    }

    @PostMapping("/permissionGrant")
    @ApiOperation(value = "给角色赋予菜单权限", position = 13)
    public CommonResult permissionGrant(@Validated @RequestBody SysRoleGrantParam sysRoleGrantParam) {
        sysRoleService.permissionGrant(sysRoleGrantParam);
        return CommonResult.success();
    }

    @PostMapping("setEnable/{roleId}")
    @ApiOperation("启用/禁用")
    public CommonResult setEnable(@ApiParam(name = "roleId", value = "主键ID", required = true) @PathVariable(value = "roleId") Integer roleId, @ApiParam(name = "isEnable", value = "是否启用", required = true) @RequestParam("isEnable") Boolean isEnable) {
        sysRoleService.setIsEnable(roleId, isEnable);
        return CommonResult.success();
    }
}

