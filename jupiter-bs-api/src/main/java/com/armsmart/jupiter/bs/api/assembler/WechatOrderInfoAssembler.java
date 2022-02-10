package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.WechatOrderInfo;
import com.armsmart.jupiter.bs.api.dto.response.WechatOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.WechatOrderInfoDecorator;
import java.util.List;

/**
 * WechatOrderInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(WechatOrderInfoDecorator.class)
public interface  WechatOrderInfoAssembler {

    WechatOrderInfoAssembler INSTANCE = Mappers.getMapper(WechatOrderInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.WechatOrderInfo
     * @date 2020/01/01
     */
    WechatOrderInfo getWechatOrderInfo(WechatOrderInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.WechatOrderInfo
     * @date 2020/01/01
     */
    WechatOrderInfo getWechatOrderInfo(WechatOrderInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<WechatOrderInfoDetail> getWechatOrderInfoDetailList(List<WechatOrderInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.WechatOrderInfoDetail
     * @date 2020/01/01
     */
    WechatOrderInfoDetail getWechatOrderInfoDetail(WechatOrderInfo entity);
}



