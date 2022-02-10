package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.UserInfoDecorator;
import com.armsmart.jupiter.bs.api.dto.request.UserInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.response.UserInfoDetail;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.dto.request.UserInfoUpdateParam;

import java.util.List;

/**
 * UserInfo 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(UserInfoDecorator.class)
public interface  UserInfoAssembler {

    UserInfoAssembler INSTANCE = Mappers.getMapper(UserInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return UserInfo
     * @date 2021/03/01
     */
    UserInfo getUserInfo(UserInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return UserInfo
     * @date 2021/03/01
     */
    UserInfo getUserInfo(UserInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/03/01
     */
    List<UserInfoDetail> getUserInfoDetailList(List<UserInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.web.controller.dto.UserInfoDetail
     * @date 2021/03/01
     */
    UserInfoDetail getUserInfoDetail(UserInfo entity);
}



