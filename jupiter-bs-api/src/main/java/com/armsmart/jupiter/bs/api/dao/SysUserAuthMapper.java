package com.armsmart.jupiter.bs.api.dao;

import org.apache.ibatis.annotations.Mapper;
import com.armsmart.jupiter.bs.api.entity.SysUserAuth;
import com.armsmart.jupiter.bs.api.dto.request.SysUserAuthQueryParam;

import java.util.List;
import java.io.Serializable;


/**
 * SysUserAuth mapper接口
 *
 * @author 苏礼刚
 **/
@Mapper
public interface SysUserAuthMapper {

    /**
     * 列表查询
     *
     * @param sysUserAuthQueryParam sysUserAuthQueryParam
     * @return java.util.List
     */
    List<SysUserAuth> selectList(SysUserAuthQueryParam sysUserAuthQueryParam);

    /**
     * 根据主键查询
     *
     * @param id 主键ID
     * @return com.armsmart.jupiter.bs.api.entity.SysUserAuth
     */
    SysUserAuth selectById(Serializable id);

    /**
     * 根据身份标识查询
     *
     * @param identifier
     * @return com.armsmart.jupiter.bs.api.entity.SysUserAuth
     * @date 2021/9/5
     */
    SysUserAuth selectByIdentifier(String identifier);

    /**
     * 根据用户ID查询
     *
     * @param userId
     * @return com.armsmart.jupiter.bs.api.entity.SysUserAuth
     * @date 2021/9/5
     */
    List<SysUserAuth> selectByUserId(Integer userId);

    /**
     * 新增
     *
     * @param sysUserAuth sysUserAuth
     * @return int
     */
    int insert(SysUserAuth sysUserAuth);

    /**
     * 更新（包含null）
     *
     * @param sysUserAuth sysUserAuth
     * @return int
     */
    int update(SysUserAuth sysUserAuth);

    /**
     * 更新（不包含null）
     *
     * @param sysUserAuth sysUserAuth
     * @return int
     */
    int updateSelective(SysUserAuth sysUserAuth);
}
