package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.UserAuthDecorator;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.UserAuthDetail;
import com.armsmart.jupiter.bs.api.entity.UserAuth;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * UserAuth 对象适配器
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(UserAuthDecorator.class)
public interface  UserAuthAssembler {

    UserAuthAssembler INSTANCE = Mappers.getMapper(UserAuthAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return UserAuth
     * @date 2021/03/01
     */
    UserAuth getUserAuth(UserAuthAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return UserAuth
     * @date 2021/03/01
     */
    UserAuth getUserAuth(UserAuthUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/03/01
     */
    List<UserAuthDetail> getUserAuthDetailList (List<UserAuth> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.web.controller.dto.UserAuthDetail
     * @date 2021/03/01
     */
    UserAuthDetail getUserAuthDetail (UserAuth entity);
}



