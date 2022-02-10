package com.armsmart.jupiter.bs.api.assembler;

import cn.hutool.json.JSONUtil;
import com.armsmart.jupiter.bs.api.assembler.decorator.BidInfoDecorator;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoListQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.BidInfoUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.BidInfoDetail;
import com.armsmart.jupiter.bs.api.entity.BidInfo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * BidInfo 对象适配器
 *
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(BidInfoDecorator.class)
public interface BidInfoAssembler {

    BidInfoAssembler INSTANCE = Mappers.getMapper(BidInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return BidInfo
     * @date 2020/01/01
     */
    BidInfo getBidInfo(BidInfoAddParam param);

    /**
     * 出价列表查询参数转换
     *
     * @param param
     * @return BidInfoQueryParam
     */
    BidInfoQueryParam getBidInfoQueryParam(BidInfoListQueryParam param);

    /**
     * 修改DTO转entity
     *
     * @param param
     * @return BidInfo
     * @date 2020/01/01
     */
    BidInfo getBidInfo(BidInfoUpdateParam param);

    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<BidInfoDetail> getBidInfoDetailList(List<BidInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.web.controller.dto.BidInfoDetail
     * @date 2020/01/01
     */
    BidInfoDetail getBidInfoDetail(BidInfo entity);

    /**
     * string转list
     *
     * @param str
     * @return java.util.List<java.lang.String>
     */
    default List<String> str2List(String str) {
        if (StringUtils.hasText(str)) {
            return JSONUtil.parseArray(str).toList(String.class);
        }
        return Collections.emptyList();
    }

    /**
     * list转string
     *
     * @param list
     * @return java.lang.String
     */
    default String list2Str(List<String> list) {
        if (!CollectionUtils.isEmpty(list)) {
            return JSONUtil.toJsonStr(list);
        }
        return "";
    }
}



