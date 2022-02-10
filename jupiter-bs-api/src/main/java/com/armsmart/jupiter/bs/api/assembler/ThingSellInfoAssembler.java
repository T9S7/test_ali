package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.cache.model.ThingSellInfoCache;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.dto.response.ThingSellInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.ThingSellInfoDecorator;
import java.util.List;

/**
 * ThingSellInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(ThingSellInfoDecorator.class)
public interface  ThingSellInfoAssembler {

    ThingSellInfoAssembler INSTANCE = Mappers.getMapper(ThingSellInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ThingSellInfo
     * @date 2020/01/01
     */
    ThingSellInfo getThingSellInfo(ThingSellInfoAddParam param);

    /**
     * entity转cache
     * @date 2021/10/24
     * @param param
     * @return com.armsmart.jupiter.bs.api.cache.model.ThingSellInfoCache
    */
    ThingSellInfoCache getThingSellInfoCache(ThingSellInfo param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.ThingSellInfo
     * @date 2020/01/01
     */
    ThingSellInfo getThingSellInfo(ThingSellInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<ThingSellInfoDetail> getThingSellInfoDetailList(List<ThingSellInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.ThingSellInfoDetail
     * @date 2020/01/01
     */
    ThingSellInfoDetail getThingSellInfoDetail(ThingSellInfo entity);
}



