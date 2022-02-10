package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.dto.response.NfcOrderInfoDetail;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.OrderInfo;
import com.armsmart.jupiter.bs.api.dto.response.OrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.OrderInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.OrderInfoDecorator;
import java.util.List;

/**
 * OrderInfo 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(OrderInfoDecorator.class)
public interface  OrderInfoAssembler {

    OrderInfoAssembler INSTANCE = Mappers.getMapper(OrderInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.OrderInfo
     * @date 2020/01/01
     */
    OrderInfo getOrderInfo(OrderInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.OrderInfo
     * @date 2020/01/01
     */
    OrderInfo getOrderInfo(OrderInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<OrderInfoDetail> getOrderInfoDetailList(List<OrderInfo> list);

    List<NfcOrderInfoDetail> getNfcOrderInfoDetailList(List<OrderInfoDetail> list);
    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.OrderInfoDetail
     * @date 2020/01/01
     */
    OrderInfoDetail getOrderInfoDetail(OrderInfo entity);
}



