package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.UserBankInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.UserBankInfo;
import com.armsmart.jupiter.bs.api.dto.response.UserBankInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserBankInfoUpdateParam;
import java.util.List;

/**
 * UserBankInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  UserBankInfoDecorator implements UserBankInfoAssembler {

    private UserBankInfoAssembler delegate;

    public UserBankInfoDecorator(UserBankInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public UserBankInfo getUserBankInfo(UserBankInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        UserBankInfo userBankInfo = this.delegate.getUserBankInfo(param);
        userBankInfo.setCreateTime(System.currentTimeMillis());
        userBankInfo.setIsDel(false);
        return userBankInfo;
    }
}



