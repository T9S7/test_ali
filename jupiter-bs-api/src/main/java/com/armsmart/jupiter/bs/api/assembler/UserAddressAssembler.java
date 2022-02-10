package com.armsmart.jupiter.bs.api.assembler;

import com.armsmart.jupiter.bs.api.assembler.decorator.UserAddressDecorator;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressMyQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressQueryParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressUpdateParam;
import com.armsmart.jupiter.bs.api.dto.response.UserAddressDetail;
import com.armsmart.jupiter.bs.api.entity.UserAddress;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.util.List;

/**
 * UserAddress 对象适配器
 *
 * @author wei.lin
 **/
@Mapper
@DecoratedWith(UserAddressDecorator.class)
public interface UserAddressAssembler {

    UserAddressAssembler INSTANCE = Mappers.getMapper(UserAddressAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return UserAddress
     * @date 2021/03/01
     */
    UserAddress getUserAddress(UserAddressAddParam param);

    /**
     * 修改DTO转entity
     *
     * @param param
     * @return UserAddress
     * @date 2021/03/01
     */
    UserAddress getUserAddress(UserAddressUpdateParam param);

    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2021/03/01
     */
    List<UserAddressDetail> getUserAddressDetailList(List<UserAddress> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.abas.web.controller.dto.UserAddressDetail
     * @date 2021/03/01
     */
    UserAddressDetail getUserAddressDetail(UserAddress entity);

    /**
     * 查询参数转换
     *
     * @param param
     * @return UserAddressQueryParam
     */
    UserAddressQueryParam getUserAddressQueryParam(UserAddressMyQueryParam param);
}



