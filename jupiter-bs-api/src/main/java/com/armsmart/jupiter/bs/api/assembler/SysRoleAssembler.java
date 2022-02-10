package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.SysRoleDecorator;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysRoleDetail;
import com.armsmart.jupiter.bs.api.entity.SysRole;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysRole 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(SysRoleDecorator.class)
public interface  SysRoleAssembler {

    SysRoleAssembler INSTANCE = Mappers.getMapper(SysRoleAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return SysRole
     * @date 2021/03/01
     */
    SysRole getSysRole(SysRoleAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return SysRole
     * @date 2021/03/01
     */
    SysRole getSysRole(SysRoleUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/03/01
     */
    List<SysRoleDetail> getSysRoleDetailList(List<SysRole> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.controller.dto.SysRoleParam
     * @date 2021/03/01
     */
    SysRoleDetail getSysRoleDetail(SysRole entity);

}



