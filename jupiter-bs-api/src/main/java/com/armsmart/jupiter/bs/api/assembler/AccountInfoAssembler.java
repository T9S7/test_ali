package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.AccountInfo;
import com.armsmart.jupiter.bs.api.dto.response.AccountInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.AccountInfoDecorator;
import java.util.List;

/**
 * AccountInfo 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(AccountInfoDecorator.class)
public interface  AccountInfoAssembler {

    AccountInfoAssembler INSTANCE = Mappers.getMapper(AccountInfoAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AccountInfo
     * @date 2020/01/01
     */
    AccountInfo getAccountInfo(AccountInfoAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AccountInfo
     * @date 2020/01/01
     */
    AccountInfo getAccountInfo(AccountInfoUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<AccountInfoDetail> getAccountInfoDetailList(List<AccountInfo> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.AccountInfoDetail
     * @date 2020/01/01
     */
    AccountInfoDetail getAccountInfoDetail(AccountInfo entity);
}



