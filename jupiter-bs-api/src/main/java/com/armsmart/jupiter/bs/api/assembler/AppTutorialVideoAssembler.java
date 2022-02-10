package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.AppTutorialVideo;
import com.armsmart.jupiter.bs.api.dto.response.AppTutorialVideoDetail;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.AppTutorialVideoDecorator;
import java.util.List;

/**
 * AppTutorialVideo 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(AppTutorialVideoDecorator.class)
public interface  AppTutorialVideoAssembler {

    AppTutorialVideoAssembler INSTANCE = Mappers.getMapper(AppTutorialVideoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppTutorialVideo
     * @date 2020/01/01
     */
    AppTutorialVideo getAppTutorialVideo(AppTutorialVideoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppTutorialVideo
     * @date 2020/01/01
     */
    AppTutorialVideo getAppTutorialVideo(AppTutorialVideoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<AppTutorialVideoDetail> getAppTutorialVideoDetailList(List<AppTutorialVideo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.AppTutorialVideoDetail
     * @date 2020/01/01
     */
    AppTutorialVideoDetail getAppTutorialVideoDetail(AppTutorialVideo entity);
}



