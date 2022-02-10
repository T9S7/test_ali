package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.UserBankInfo;
import com.armsmart.jupiter.bs.api.dto.response.UserBankInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.UserBankInfoDecorator;
import java.util.List;

/**
 * UserBankInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(UserBankInfoDecorator.class)
public interface  UserBankInfoAssembler {

    UserBankInfoAssembler INSTANCE = Mappers.getMapper(UserBankInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.UserBankInfo
     * @date 2020/01/01
     */
    UserBankInfo getUserBankInfo(UserBankInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.UserBankInfo
     * @date 2020/01/01
     */
    UserBankInfo getUserBankInfo(UserBankInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<UserBankInfoDetail> getUserBankInfoDetailList(List<UserBankInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.UserBankInfoDetail
     * @date 2020/01/01
     */
    UserBankInfoDetail getUserBankInfoDetail(UserBankInfo entity);
}



