package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.AccountInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.AccountInfo;
import com.armsmart.jupiter.bs.api.dto.response.AccountInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AccountInfoUpdateParam;
import java.util.List;

/**
 * AccountInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  AccountInfoDecorator implements AccountInfoAssembler {

    private AccountInfoAssembler delegate;

    public AccountInfoDecorator(AccountInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public AccountInfo getAccountInfo(AccountInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        AccountInfo accountInfo = this.delegate.getAccountInfo(param);
        accountInfo.setCreateTime(System.currentTimeMillis());
        accountInfo.setIsDel(false);
        return accountInfo;
    }
}



