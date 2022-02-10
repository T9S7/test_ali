package com.armsmart.jupiter.bs.api.assembler;


import com.armsmart.jupiter.bs.api.assembler.decorator.SysRegionDecorator;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRegionUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SysRegionDetail;
import com.armsmart.jupiter.bs.api.entity.SysRegion;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SysRegion 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(SysRegionDecorator.class)
public interface SysRegionAssembler {

    SysRegionAssembler INSTANCE = Mappers.getMapper(SysRegionAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.abas.app.entity.SysRegion
     * @date 2020/03/04
     */
    SysRegion getSysRegion(SysRegionAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.abas.app.entity.SysRegion
     * @date 2020/03/04
     */
    SysRegion getSysRegion(SysRegionUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/03/04
     */
    List<SysRegionDetail> getSysRegionDetailList (List<SysRegion> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.app.controller.dto.SysRegionDetail
     * @date 2020/03/04
     */
    SysRegionDetail getSysRegionDetail (SysRegion entity);
}



