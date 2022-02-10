package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.AppConfigureInfo;
import com.armsmart.jupiter.bs.api.dto.response.AppConfigureInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.AppConfigureInfoDecorator;
import java.util.List;

/**
 * AppConfigureInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(AppConfigureInfoDecorator.class)
public interface  AppConfigureInfoAssembler {

    AppConfigureInfoAssembler INSTANCE = Mappers.getMapper(AppConfigureInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppConfigureInfo
     * @date 2020/01/01
     */
    AppConfigureInfo getAppConfigureInfo(AppConfigureInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppConfigureInfo
     * @date 2020/01/01
     */
    AppConfigureInfo getAppConfigureInfo(AppConfigureInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<AppConfigureInfoDetail> getAppConfigureInfoDetailList(List<AppConfigureInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.AppConfigureInfoDetail
     * @date 2020/01/01
     */
    AppConfigureInfoDetail getAppConfigureInfoDetail(AppConfigureInfo entity);
}



