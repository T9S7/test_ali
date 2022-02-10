package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.ArtPicInfoDecorator;
import com.armsmart.jupiter.bs.api.dto.request.ArtPicInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtPicInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ArtPicInfoDetail;
import com.armsmart.jupiter.bs.api.entity.ArtPicInfo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ArtPicInfo 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(ArtPicInfoDecorator.class)
public interface ArtPicInfoAssembler {

    ArtPicInfoAssembler INSTANCE = Mappers.getMapper(ArtPicInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return ArtPicInfo
     * @date 2020/01/01
     */
    ArtPicInfo getArtPicInfo(ArtPicInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return ArtPicInfo
     * @date 2020/01/01
     */
    ArtPicInfo getArtPicInfo(ArtPicInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<ArtPicInfoDetail> getArtPicInfoDetailList(List<ArtPicInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.web.controller.dto.ArtPicInfoDetail
     * @date 2020/01/01
     */
    ArtPicInfoDetail getArtPicInfoDetail(ArtPicInfo entity);
}



