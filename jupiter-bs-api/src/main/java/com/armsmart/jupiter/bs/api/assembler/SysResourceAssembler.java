package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.SysResourceDecorator;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.entity.SysResource;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysResource 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(SysResourceDecorator.class)
public interface  SysResourceAssembler {

    SysResourceAssembler INSTANCE = Mappers.getMapper(SysResourceAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return SysResource
     * @date 2021/03/01
     */
    SysResource getSysResource(SysResourceAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return SysResource
     * @date 2021/03/01
     */
    SysResource getSysResource(SysResourceUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/03/01
     */
    List<SysResourceDetail> getSysResourceDetailList(List<SysResource> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.controller.dto.SysResourceParam
     * @date 2021/03/01
     */
    SysResourceDetail getSysResourceDetail(SysResource entity);
}



