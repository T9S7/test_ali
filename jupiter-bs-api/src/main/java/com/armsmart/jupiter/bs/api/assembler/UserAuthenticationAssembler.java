package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.UserAuthenticationDecorator;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationBindParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.UserAuthenticationDetail;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * UserAuthentication 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(UserAuthenticationDecorator.class)
public interface UserAuthenticationAssembler {

    UserAuthenticationAssembler INSTANCE = Mappers.getMapper(UserAuthenticationAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return UserAuthentication
     * @date 2020/01/01
     */
    UserAuthentication getUserAuthentication(UserAuthenticationAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return UserAuthentication
     * @date 2020/01/01
     */
    UserAuthentication getUserAuthentication(UserAuthenticationUpdateParam param);
    /**
     * 绑定DTO转entity
     *
     * @param param
     * @return UserAuthentication
     * @date 2020/01/01
     */
    UserAuthentication getUserAuthentication(UserAuthenticationBindParam param);

    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<UserAuthenticationDetail> getUserAuthenticationDetailList(List<UserAuthentication> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.web.controller.dto.UserAuthenticationDetail
     * @date 2020/01/01
     */
    UserAuthenticationDetail getUserAuthenticationDetail(UserAuthentication entity);
}



