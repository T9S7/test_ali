package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.dto.request.AppVersionUpdateParam;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.AppVersion;
import com.armsmart.jupiter.bs.api.dto.response.AppVersionDetail;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionAddParam;
//import com.armsmart.jupiter.bs.api.dto.request.AppVersionUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.AppVersionDecorator;
import java.util.List;

/**
 * AppVersion 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(AppVersionDecorator.class)
public interface  AppVersionAssembler {

    AppVersionAssembler INSTANCE = Mappers.getMapper(AppVersionAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppVersion
     * @date 2020/01/01
     */
    AppVersion getAppVersion(AppVersionAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppVersion
     * @date 2020/01/01
     */
    AppVersion getAppVersion(AppVersionUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<AppVersionDetail> getAppVersionDetailList(List<AppVersion> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.AppVersionDetail
     * @date 2020/01/01
     */
    AppVersionDetail getAppVersionDetail(AppVersion entity);
}



