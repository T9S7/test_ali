package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.CheckInfo;
import com.armsmart.jupiter.bs.api.dto.response.CheckInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.CheckInfoDecorator;
import java.util.List;

/**
 * CheckInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(CheckInfoDecorator.class)
public interface  CheckInfoAssembler {

    CheckInfoAssembler INSTANCE = Mappers.getMapper(CheckInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.CheckInfo
     * @date 2020/01/01
     */
    CheckInfo getCheckInfo(CheckInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.CheckInfo
     * @date 2020/01/01
     */
    CheckInfo getCheckInfo(CheckInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<CheckInfoDetail> getCheckInfoDetailList(List<CheckInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.CheckInfoDetail
     * @date 2020/01/01
     */
    CheckInfoDetail getCheckInfoDetail(CheckInfo entity);
}



