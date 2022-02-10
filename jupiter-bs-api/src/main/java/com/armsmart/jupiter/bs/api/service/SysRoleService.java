package com.armsmart.jupiter.bs.api.service;


import com.armsmart.jupiter.bs.api.constants.ResourceType;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.dto.response.SysRoleDetail;
import com.armsmart.jupiter.bs.api.entity.SysRole;
import com.armsmart.jupiter.bs.api.entity.SysRolePermission;
import com.armsmart.common.api.CommonPage;
import com.armsmart.jupiter.bs.api.assembler.SysResourceAssembler;
import com.armsmart.jupiter.bs.api.assembler.SysRoleAssembler;
import com.armsmart.common.exception.BusinessException;
import com.armsmart.jupiter.bs.api.dao.SysRoleMapper;
import com.armsmart.jupiter.bs.api.dao.SysRolePermissionMapper;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleGrantParam;
import com.armsmart.jupiter.bs.api.entity.SysResource;
import com.armsmart.jupiter.bs.api.error.ManageError;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SysRole service
 *
 * @author wei.lin
 **/
@Slf4j
@Service
public class SysRoleService {

    @Autowired(required = false)
    private SysRoleMapper sysRoleMapper;
    @Autowired(required = false)
    private SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    private SysResourceService sysResourceService;


    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return CommonPage
     * @date 2021/03/01
     */
    public CommonPage<SysRoleDetail> selectPage(SysRoleQueryParam query) {
        query.setIsDel(false);
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        PageInfo<SysRole> pageInfo = new PageInfo<>(sysRoleMapper.selectList(query));
        List<SysRoleDetail> dtoList = SysRoleAssembler.INSTANCE.getSysRoleDetailList(pageInfo.getList());
        return CommonPage.restPage(pageInfo, dtoList);
    }

    /**
     * 查询所有角色
     *
     * @return java.util.List<SysRoleDetail>
     */
    public List<SysRoleDetail> selectAll() {
        SysRoleQueryParam query = new SysRoleQueryParam();
        query.setIsDel(false);
        List<SysRole> list = sysRoleMapper.selectList(query);
        List<SysRoleDetail> dtoList = SysRoleAssembler.INSTANCE.getSysRoleDetailList(list);
        return dtoList;
    }

    /**
     * 插入数据
     *
     * @param sysRoleAddParam
     * @return SysRole
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public Serializable insert(SysRoleAddParam sysRoleAddParam) {
        SysRole entity = SysRoleAssembler.INSTANCE.getSysRole(sysRoleAddParam);
        SysRole sysRole = sysRoleMapper.selectByName(sysRoleAddParam.getRoleName());
        if (null != sysRole) {
            throw new BusinessException(ManageError.ROLE_NAME_IS_EXIST);
        }
        SysRole typeRole = sysRoleMapper.selectByType(sysRoleAddParam.getRoleType());
        if (null != typeRole) {
            throw new BusinessException(ManageError.ROLE_TYPE_IS_USED);
        }
        sysRoleMapper.insert(entity);
        int roleId = entity.getRoleId();
        List<SysRolePermission> rolePermissions = getSysRolePermissions(roleId, sysRoleAddParam.getResourceIds());
        sysRolePermissionMapper.insertList(rolePermissions);
        return roleId;
    }

    /**
     * 修改数据
     *
     * @param sysRoleUpdateParam
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleUpdateParam sysRoleUpdateParam) {
        SysRole entity = SysRoleAssembler.INSTANCE.getSysRole(sysRoleUpdateParam);
        int roleId = entity.getRoleId();
        SysRole oldRole = sysRoleMapper.selectById(roleId);
        if (null == oldRole) {
            throw new BusinessException(ManageError.ROLE_NOT_EXIST_OR_DEL);
        }
        if (!Objects.equals(oldRole.getRoleName(), sysRoleUpdateParam.getRoleName())) {
            SysRole sysRole = sysRoleMapper.selectByName(sysRoleUpdateParam.getRoleName());
            if (null != sysRole) {
                throw new BusinessException(ManageError.ROLE_NAME_IS_EXIST);
            }
        }
        if (!Objects.equals(oldRole.getRoleType(), sysRoleUpdateParam.getRoleType())) {
            SysRole typeRole = sysRoleMapper.selectByType(sysRoleUpdateParam.getRoleType());
            if (null != typeRole) {
                throw new BusinessException(ManageError.ROLE_TYPE_IS_USED);
            }
        }
        sysRolePermissionMapper.deleteByRoleId(entity.getRoleId());
        List<SysRolePermission> rolePermissions = getSysRolePermissions(roleId, sysRoleUpdateParam.getResourceIds());
        sysRolePermissionMapper.insertList(rolePermissions);
        sysRoleMapper.updateSelective(entity);
    }

    /**
     * 组装角色权限集合
     *
     * @param roleId      角色ID
     * @param resourceIds 资源ID集合
     * @return java.util.List
     * @date 2021/1/2
     */
    private List<SysRolePermission> getSysRolePermissions(int roleId, List<Integer> resourceIds) {
        long currentTime = System.currentTimeMillis();
        return resourceIds.stream().map(resourceId -> {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setResourceId(resourceId);
            rolePermission.setRoleId(roleId);
            rolePermission.setCreateTime(currentTime);
            return rolePermission;
        }).collect(Collectors.toList());
    }


    /**
     * 根据主键删除
     *
     * @param roleId
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer roleId) {
        SysRole entity = new SysRole();
        entity.setRoleId(roleId);
        entity.setIsDel(true);
        sysRoleMapper.updateSelective(entity);
    }

    /**
     * 根据主键批量删除
     *
     * @param ids 主键集合
     * @date 2021/03/01
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDel(List<Integer> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            ids.forEach(id -> {
                deleteById(id);
            });
        }
    }

    /**
     * 根据角色ID查询
     *
     * @param roleId
     * @return com.armsmart.abas.dto.response.SysRole
     */
    public SysRole selectById(Integer roleId) {
        return sysRoleMapper.selectById(roleId);
    }

    /**
     * 查询角色权限树
     *
     * @param roleId      角色ID
     * @param containsBtn 是否包含按钮
     * @return SysRoleDetail
     * @date 2021/03/01
     */

    public List<SysResourceDetail> selectRolePermissionTree(Integer roleId, boolean containsBtn) {
        List<SysResourceDetail> rolePermissions = selectRolePermission(roleId);
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
        if (!containsBtn) {
            rolePermissions = rolePermissions.stream().filter(sysResource -> sysResource.getResourceType() == ResourceType.MENU.getCode()).collect(Collectors.toList());
        }
        List<SysResourceDetail> resourceDetails = sysResourceService.buildTree(rolePermissions);
        return resourceDetails;
    }

    /**
     * 查询角色权限平铺列表
     *
     * @return java.util.List<SysResource>
     */
    public List<SysResourceDetail> selectRolePermission(Integer roleId) {
        List<SysResource> resultList = sysRolePermissionMapper.selectRolePermission(roleId);
        return SysResourceAssembler.INSTANCE.getSysResourceDetailList(resultList);
    }

    /**
     * 查询角色按钮权限平铺列表
     *
     * @return java.util.List<SysResource>
     */
    public List<SysResourceDetail> selectRoleBtnPermission(Integer roleId) {
        List<SysResource> rolePermissions = sysRolePermissionMapper.selectRolePermission(roleId);
        List<SysResource> resultList = rolePermissions.stream().filter(sysResource -> sysResource.getResourceType() != ResourceType.MENU.getCode()).collect(Collectors.toList());
        return SysResourceAssembler.INSTANCE.getSysResourceDetailList(resultList);
    }

    /**
     * 角色增量赋权
     *
     * @param sysRoleGrantParam
     */
    public void permissionGrant(SysRoleGrantParam sysRoleGrantParam) {
        Integer roleId = sysRoleGrantParam.getRoleId();
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if (null == sysRole) {
            throw new BusinessException(ManageError.ROLE_NOT_EXIST_OR_DEL);
        }
        List<SysRolePermission> list = new ArrayList<>();
        sysRoleGrantParam.getResourceIds().stream().forEach(item -> {
            SysRolePermission sysRolePermission = sysRolePermissionMapper.selectByResourceId(item);
            if (null == sysRolePermission) {
                sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(roleId);
                sysRolePermission.setResourceId(item);
                sysRolePermission.setCreateTime(System.currentTimeMillis());
                list.add(sysRolePermission);
            }
        });
        if (!CollectionUtils.isEmpty(list)) {
            sysRolePermissionMapper.insertList(list);
        }
    }

    /**
     * 设置启用/禁用
     *
     * @param roleId   角色ID
     * @param isEnable true表示启用
     * @return void
     */
    public void setIsEnable(Integer roleId, boolean isEnable) {
        SysRole entity = new SysRole();
        entity.setRoleId(roleId);
        entity.setIsEnable(isEnable);
        sysRoleMapper.updateSelective(entity);
    }

}
