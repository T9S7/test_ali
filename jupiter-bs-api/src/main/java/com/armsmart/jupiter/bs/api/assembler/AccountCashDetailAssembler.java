package com.armsmart.jupiter.bs.api.assembler;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.armsmart.jupiter.bs.api.entity.AccountCashDetail;
import com.armsmart.jupiter.bs.api.dto.response.AccountCashDetailDetail;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailUpdateParam;
import com.armsmart.jupiter.bs.api.assembler.decorator.AccountCashDetailDecorator;
import java.util.List;

/**
 * AccountCashDetail 对象适配器
 * @author 苏礼刚
 **/
@Mapper
@DecoratedWith(AccountCashDetailDecorator.class)
public interface  AccountCashDetailAssembler {

    AccountCashDetailAssembler INSTANCE = Mappers.getMapper(AccountCashDetailAssembler.class);

    /**
     * 添加DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AccountCashDetail
     * @date 2020/01/01
     */
    AccountCashDetail getAccountCashDetail(AccountCashDetailAddParam param);
    /**
     * 修改DTO转entity
     *
     * @param param
     * @return com.armsmart.jupiter.bs.api.entity.AccountCashDetail
     * @date 2020/01/01
     */
    AccountCashDetail getAccountCashDetail(AccountCashDetailUpdateParam param);
    /**
     * entity集合转详情DTO集合
     *
     * @param list
     * @return java.util.List
     * @date 2020/01/01
     */
    List<AccountCashDetailDetail> getAccountCashDetailDetailList(List<AccountCashDetail> list);

    /**
     * entity转DTO
     *
     * @param entity
     * @return com.armsmart.jupiter.bs.api.controller.dto.AccountCashDetailDetail
     * @date 2020/01/01
     */
    AccountCashDetailDetail getAccountCashDetailDetail(AccountCashDetail entity);
}



