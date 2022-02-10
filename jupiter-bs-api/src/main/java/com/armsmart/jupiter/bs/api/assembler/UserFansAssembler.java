package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.UserFans;
import com.armsmart.jupiter.bs.api.dto.response.UserFansDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserFansAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserFansUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.UserFansDecorator;
import java.util.List;

/**
 * UserFans 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(UserFansDecorator.class)
public interface  UserFansAssembler {

    UserFansAssembler INSTANCE = Mappers.getMapper(UserFansAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.UserFans
     * @date 2020/01/01
     */
    UserFans getUserFans(UserFansAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.UserFans
     * @date 2020/01/01
     */
    UserFans getUserFans(UserFansUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<UserFansDetail> getUserFansDetailList(List<UserFans> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.UserFansDetail
     * @date 2020/01/01
     */
    UserFansDetail getUserFansDetail(UserFans entity);
}



