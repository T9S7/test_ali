package com.armsmart.jupiter.bs.api.dao;

import com.armsmart.jupiter.bs.api.entity.SysUser;
import com.armsmart.jupiter.bs.api.dto.request.SysUserQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.io.Serializable;


/**
 * SysUser mapper接口
 *
 * @author wei.lin
 **/
@Mapper
public interface SysUserMapper {

    /**
     * 列表查询
     *
     * @param sysUserQueryParam sysUserQueryParam
     * @return java.util.List
     */
    List<SysUser> selectList(SysUserQueryParam sysUserQueryParam);

    /**
     * 根据主键查询
     *
     * @param userId 主键ID
     * @return SysUser
     */
    SysUser selectById(Serializable userId);

    /**
     * 根据用户名查询
     *
     * @param username
     * @return SysUser
     */
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 新增
     *
     * @param sysUser sysUser
     * @return int
     */
    int insert(SysUser sysUser);

    /**
     * 更新（包含null）
     *
     * @param sysUser sysUser
     * @return int
     */
    int update(SysUser sysUser);

    /**
     * 更新（不包含null）
     *
     * @param sysUser sysUser
     * @return int
     */
    int updateSelective(SysUser sysUser);

}
