package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.entity.SysRolePermission;
import com.armsmart.jupiter.bs.api.entity.SysResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.io.Serializable;


/**
 * SysRolePermission mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface SysRolePermissionMapper {


    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return SysRolePermission
     */
    SysRolePermission selectById(Serializable id);

    /**
     * 新增
     *
     * @param sysRolePermission sysRolePermission
     * @return int
     */
    int insert(SysRolePermission sysRolePermission);

    /**
     * 批量新增
     *
     * @param sysRolePermissions sysRolePermissions
     * @return int
     */
    int insertList(List<SysRolePermission> sysRolePermissions);

    /**
     * 更新（包含null）
     *
     * @param sysRolePermission sysRolePermission
     * @return int
     */
    int update(SysRolePermission sysRolePermission);

    /**
     * 更新（不包含null）
     *
     * @param sysRolePermission sysRolePermission
     * @return int
     */
    int updateSelective(SysRolePermission sysRolePermission);

    /**
     * 根据角色ID删除权限
     *
     * @param roleId 角色ID
     * @return int
     * @date 2021/1/2
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据角色ID查询权限
     *
     * @param roleId
     * @return java.util.List
     * @date 2021/1/2
     */
    List<SysResource> selectRolePermission(@Param("roleId") Integer roleId);

    /**
     * 根据资源ID查询
     *
     * @param resourceId
     * @return SysRolePermission
     */
    SysRolePermission selectByResourceId(@Param("resourceId") Integer resourceId);
}
