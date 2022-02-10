package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.SmsInfoDecorator;
import com.armsmart.jupiter.bs.api.dto.request.SmsInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SmsInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.SmsInfoDetail;
import com.armsmart.jupiter.bs.api.entity.SmsInfo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * SmsInfo 对象适配器
 *
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(SmsInfoDecorator.class)
public interface SmsInfoAssembler {

    SmsInfoAssembler INSTANCE = Mappers.getMapper(SmsInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return SmsInfo
     * @date 2021/03/01
     */
    SmsInfo getSmsInfo(SmsInfoAddParam param);

    /**
     * 修改DTO转entity
     *
     * @param param
     * @return SmsInfo
     * @date 2021/03/01
     */
    SmsInfo getSmsInfo(SmsInfoUpdateParam param);

    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/03/01
     */
    List<SmsInfoDetail> getSmsInfoDetailList(List<SmsInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.web.controller.dto.SmsInfoDetail
     * @date 2021/03/01
     */
    SmsInfoDetail getSmsInfoDetail(SmsInfo entity);
}



