package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.RefundInfo;
import com.armsmart.jupiter.bs.api.dto.response.RefundInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.RefundInfoDecorator;
import java.util.List;

/**
 * RefundInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(RefundInfoDecorator.class)
public interface  RefundInfoAssembler {

    RefundInfoAssembler INSTANCE = Mappers.getMapper(RefundInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.RefundInfo
     * @date 2020/01/01
     */
    RefundInfo getRefundInfo(RefundInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.RefundInfo
     * @date 2020/01/01
     */
    RefundInfo getRefundInfo(RefundInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<RefundInfoDetail> getRefundInfoDetailList(List<RefundInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.RefundInfoDetail
     * @date 2020/01/01
     */
    RefundInfoDetail getRefundInfoDetail(RefundInfo entity);
}



