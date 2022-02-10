package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.dto.request.SysRoleQueryParam;
import com.armsmart.jupiter.bs.api.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.io.Serializable;


/**
 * SysRole mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface SysRoleMapper {

    /**
     * 列表查询
     *
     * @param sysRoleQueryParam sysRoleQueryParam
     * @return java.util.List
     */
    List<SysRole> selectList(SysRoleQueryParam sysRoleQueryParam);

    /**
     * 根据主键查询
     *
     * @param roleId 主键ID
     * @return SysRole
     */
    SysRole selectById(Serializable roleId);

    /**
     * 新增
     *
     * @param sysRole sysRole
     * @return int
     */
    int insert(SysRole sysRole);

    /**
     * 更新（包含null）
     *
     * @param sysRole sysRole
     * @return int
     */
    int update(SysRole sysRole);

    /**
     * 更新（不包含null）
     *
     * @param sysRole sysRole
     * @return int
     */
    int updateSelective(SysRole sysRole);

    /**
     * 根据角色名称查询
     *
     * @param roleName
     * @return SysRole
     */
    SysRole selectByName(String roleName);
    /**
    *根据角色类型查询
    * @param roleType
    * @return SysRole
    */
    SysRole selectByType(Integer roleType);
}
