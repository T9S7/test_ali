package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.AppNotice;
import com.armsmart.jupiter.bs.api.dto.response.AppNoticeDetail;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.AppNoticeDecorator;
import java.util.List;

/**
 * AppNotice 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(AppNoticeDecorator.class)
public interface  AppNoticeAssembler {

    AppNoticeAssembler INSTANCE = Mappers.getMapper(AppNoticeAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppNotice
     * @date 2020/01/01
     */
    AppNotice getAppNotice(AppNoticeAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AppNotice
     * @date 2020/01/01
     */
    AppNotice getAppNotice(AppNoticeUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<AppNoticeDetail> getAppNoticeDetailList(List<AppNotice> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.AppNoticeDetail
     * @date 2020/01/01
     */
    AppNoticeDetail getAppNoticeDetail(AppNotice entity);
}



