package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.ThingInfoDecorator;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ThingInfoDetail;
import com.armsmart.jupiter.bs.api.dto.response.ThingNftInfo;
import com.armsmart.jupiter.bs.api.dto.response.ThingShortInfo;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ThingInfo 对象适配器
 *
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(ThingInfoDecorator.class)
public interface ThingInfoAssembler {

    ThingInfoAssembler INSTANCE = Mappers.getMapper(ThingInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ThingInfo
     * @date 2020/01/01
     */
    ThingInfo getThingInfo(ThingInfoAddParam param);

    ThingInfo getThingInfo(ThingNfcAddParam param);

    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ThingInfo
     * @date 2020/01/01
     */
    ThingInfo getThingInfo(ThingInfoUpdateParam param);

    ThingInfo getThingInfo(ThingNfcUpdateParam param);

    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<ThingInfoDetail> getThingInfoDetailList(List<ThingInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.ThingInfoDetail
     * @date 2020/01/01
     */
    ThingInfoDetail getThingInfoDetail(ThingInfo entity);

    @Mappings({
            @Mapping(source = "entity.id",target = "thingId")
    })
    ThingShortInfo getThingShortInfo(ThingInfo entity);

    List<ThingShortInfo> getThingShortInfoList(List<ThingInfo> list);

    @Mappings({
            @Mapping(source = "entity.id",target = "thingId")
    })
    ThingNftInfo getThingNftInfo(ThingInfo entity);


}



