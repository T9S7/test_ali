package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.UserCollect;
import com.armsmart.jupiter.bs.api.dto.response.UserCollectDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.UserCollectDecorator;
import java.util.List;

/**
 * UserCollect 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(UserCollectDecorator.class)
public interface  UserCollectAssembler {

    UserCollectAssembler INSTANCE = Mappers.getMapper(UserCollectAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.UserCollect
     * @date 2020/01/01
     */
    UserCollect getUserCollect(UserCollectAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.UserCollect
     * @date 2020/01/01
     */
    UserCollect getUserCollect(UserCollectUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<UserCollectDetail> getUserCollectDetailList(List<UserCollect> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.UserCollectDetail
     * @date 2020/01/01
     */
    UserCollectDetail getUserCollectDetail(UserCollect entity);
}



