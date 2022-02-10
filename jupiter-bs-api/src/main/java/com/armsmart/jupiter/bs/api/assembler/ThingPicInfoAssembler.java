package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import com.armsmart.jupiter.bs.api.dto.response.ThingPicInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.ThingPicInfoDecorator;
import java.util.List;

/**
 * ThingPicInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(ThingPicInfoDecorator.class)
public interface  ThingPicInfoAssembler {

    ThingPicInfoAssembler INSTANCE = Mappers.getMapper(ThingPicInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ThingPicInfo
     * @date 2020/01/01
     */
    ThingPicInfo getThingPicInfo(ThingPicInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ThingPicInfo
     * @date 2020/01/01
     */
    ThingPicInfo getThingPicInfo(ThingPicInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<ThingPicInfoDetail> getThingPicInfoDetailList(List<ThingPicInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.ThingPicInfoDetail
     * @date 2020/01/01
     */
    ThingPicInfoDetail getThingPicInfoDetail(ThingPicInfo entity);
}



