package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.PushInfo;
import com.armsmart.jupiter.bs.api.dto.response.PushInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.PushInfoDecorator;
import java.util.List;

/**
 * PushInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(PushInfoDecorator.class)
public interface  PushInfoAssembler {

    PushInfoAssembler INSTANCE = Mappers.getMapper(PushInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.PushInfo
     * @date 2020/01/01
     */
    PushInfo getPushInfo(PushInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.PushInfo
     * @date 2020/01/01
     */
    PushInfo getPushInfo(PushInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<PushInfoDetail> getPushInfoDetailList(List<PushInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.PushInfoDetail
     * @date 2020/01/01
     */
    PushInfoDetail getPushInfoDetail(PushInfo entity);
}



