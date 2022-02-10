package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.ArtInfoDecorator;
import com.armsmart.jupiter.bs.api.dto.request.ArtInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.ArtInfoDetail;
import com.armsmart.jupiter.bs.api.entity.ArtInfo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * ArtInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(ArtInfoDecorator.class)
public interface ArtInfoAssembler {

    ArtInfoAssembler INSTANCE = Mappers.getMapper(ArtInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ArtInfo
     * @date 2020/01/01
     */
    ArtInfo getArtInfo(ArtInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ArtInfo
     * @date 2020/01/01
     */
    ArtInfo getArtInfo(ArtInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<ArtInfoDetail> getArtInfoDetailList(List<ArtInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.ArtInfoDetail
     * @date 2020/01/01
     */
    ArtInfoDetail getArtInfoDetail(ArtInfo entity);
}



