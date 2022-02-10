package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.AccountCashDetailAssembler;

import com.armsmart.jupiter.bs.api.entity.AccountCashDetail;
import com.armsmart.jupiter.bs.api.dto.response.AccountCashDetailDetail;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountCashDetailUpdateParam;
import java.util.List;

/**
 * AccountCashDetail 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  AccountCashDetailDecorator implements AccountCashDetailAssembler {

    private AccountCashDetailAssembler delegate;

    public AccountCashDetailDecorator(AccountCashDetailAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public AccountCashDetail getAccountCashDetail(AccountCashDetailAddParam param){
        if ( param == null ) {
            return null;
        }
        AccountCashDetail accountCashDetail = this.delegate.getAccountCashDetail(param);
        accountCashDetail.setCreateTime(System.currentTimeMillis());
        accountCashDetail.setIsDel(false);
        return accountCashDetail;
    }
}



