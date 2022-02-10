package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.SysUserDecorator;
import com.armsmart.jupiter.bs.api.dto.request.SysUserAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysUserUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysUserDetail;
import com.armsmart.jupiter.bs.api.dto.response.SysUserLoginResult;
import com.armsmart.jupiter.bs.api.entity.SysRole;
import com.armsmart.jupiter.bs.api.entity.SysUser;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysUser 对象适配器
 *
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(SysUserDecorator.class)
public interface SysUserAssembler {

    SysUserAssembler INSTANCE = Mappers.getMapper(SysUserAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return SysUser
     * @date 2021/03/01
     */
    SysUser getSysUser(SysUserAddParam param);

    /**
     * 修改DTO转entity
     *
     * @param param
     * @return SysUser
     * @date 2021/03/01
     */
    SysUser getSysUser(SysUserUpdateParam param);

    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/03/01
     */
    List<SysUserDetail> getSysUserDetailList(List<SysUser> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.controller.dto.SysUserDetail
     * @date 2021/03/01
     */
    @Mappings({
            @Mapping(source = "sysRole", target = "sysRole"),
            @Mapping(source = "entity.roleId", target = "roleId"),
            @Mapping(source = "entity.createTime", target = "createTime"),
            @Mapping(source = "entity.updateTime", target = "updateTime"),
            @Mapping(source = "entity.isEnable", target = "isEnable")
    })
    SysUserDetail getSysUserDetail(SysUser entity, SysRole sysRole);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.controller.dto.SysUserParam
     * @date 2021/03/01
     */
    SysUserDetail getSysUserDetail(SysUser entity);

    /**
     * 组装登陆结果
     *
     * @param entity
     * @param sysRole
     * @return SysUserLoginResult
     */
    @Mappings({
            @Mapping(source = "sysRole", target = "sysRole"),
            @Mapping(source = "entity.roleId", target = "roleId"),
            @Mapping(source = "token", target = "token"),
            @Mapping(source = "tokenHead", target = "tokenHead")
    })
    SysUserLoginResult getSysUserLoginResult(SysUser entity, SysRole sysRole, String token, String tokenHead);
}



