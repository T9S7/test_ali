package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.CompanyInfo;
import com.armsmart.jupiter.bs.api.dto.response.CompanyInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.CompanyInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.CompanyInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.CompanyInfoDecorator;
import java.util.List;

/**
 * CompanyInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(CompanyInfoDecorator.class)
public interface  CompanyInfoAssembler {

    CompanyInfoAssembler INSTANCE = Mappers.getMapper(CompanyInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.CompanyInfo
     * @date 2020/01/01
     */
    CompanyInfo getCompanyInfo(CompanyInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.CompanyInfo
     * @date 2020/01/01
     */
    CompanyInfo getCompanyInfo(CompanyInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<CompanyInfoDetail> getCompanyInfoDetailList(List<CompanyInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.CompanyInfoDetail
     * @date 2020/01/01
     */
    CompanyInfoDetail getCompanyInfoDetail(CompanyInfo entity);
}



