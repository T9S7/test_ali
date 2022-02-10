package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo;
import com.armsmart.jupiter.bs.api.dto.response.WechatUnifiedOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.WechatUnifiedOrderInfoDecorator;
import java.util.List;

/**
 * WechatUnifiedOrderInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(WechatUnifiedOrderInfoDecorator.class)
public interface  WechatUnifiedOrderInfoAssembler {

    WechatUnifiedOrderInfoAssembler INSTANCE = Mappers.getMapper(WechatUnifiedOrderInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo
     * @date 2020/01/01
     */
    WechatUnifiedOrderInfo getWechatUnifiedOrderInfo(WechatUnifiedOrderInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo
     * @date 2020/01/01
     */
    WechatUnifiedOrderInfo getWechatUnifiedOrderInfo(WechatUnifiedOrderInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<WechatUnifiedOrderInfoDetail> getWechatUnifiedOrderInfoDetailList(List<WechatUnifiedOrderInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.WechatUnifiedOrderInfoDetail
     * @date 2020/01/01
     */
    WechatUnifiedOrderInfoDetail getWechatUnifiedOrderInfoDetail(WechatUnifiedOrderInfo entity);
}



