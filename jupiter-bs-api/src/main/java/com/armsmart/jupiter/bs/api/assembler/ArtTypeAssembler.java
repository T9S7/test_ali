package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.ArtType;
import com.armsmart.jupiter.bs.api.dto.response.ArtTypeDetail;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.ArtTypeDecorator;
import java.util.List;

/**
 * ArtType 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(ArtTypeDecorator.class)
public interface  ArtTypeAssembler {

    ArtTypeAssembler INSTANCE = Mappers.getMapper(ArtTypeAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ArtType
     * @date 2020/01/01
     */
    ArtType getArtType(ArtTypeAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ArtType
     * @date 2020/01/01
     */
    ArtType getArtType(ArtTypeUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<ArtTypeDetail> getArtTypeDetailList(List<ArtType> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.ArtTypeDetail
     * @date 2020/01/01
     */
    ArtTypeDetail getArtTypeDetail(ArtType entity);


    List<ArtTypeDetail> getSysResourceDetailList(List<ArtType> list);
}



