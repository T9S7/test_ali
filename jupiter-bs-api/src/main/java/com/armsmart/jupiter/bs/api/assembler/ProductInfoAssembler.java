package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.dto.request.ProductInfoPutOnParam;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.ProductInfo;
import com.armsmart.jupiter.bs.api.dto.response.ProductInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.ProductInfoDecorator;
import java.util.List;

/**
 * ProductInfo 对象适配器
 * @author 林伟
 **/
@Mapper
@DecoratedWith(ProductInfoDecorator.class)
public interface  ProductInfoAssembler {

    ProductInfoAssembler INSTANCE = Mappers.getMapper(ProductInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ProductInfo
     * @date 2021/09/15
     */
    ProductInfo getProductInfo(ProductInfoAddParam param);
    /**
     * 上架
     * @date 2021/9/16
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ProductInfo
    */
    ProductInfo getProductInfo(ProductInfoPutOnParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ProductInfo
     * @date 2021/09/15
     */
    ProductInfo getProductInfo(ProductInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/09/15
     */
    List<ProductInfoDetail> getProductInfoDetailList(List<ProductInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.ProductInfoDetail
     * @date 2021/09/15
     */
    ProductInfoDetail getProductInfoDetail(ProductInfo entity);
}



