package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.UserFansAssembler;

import com.armsmart.jupiter.bs.api.entity.UserFans;
import com.armsmart.jupiter.bs.api.dto.response.UserFansDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserFansAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserFansUpdateParam;
import java.util.List;

/**
 * UserFans 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  UserFansDecorator implements UserFansAssembler {

    private UserFansAssembler delegate;

    public UserFansDecorator(UserFansAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public UserFans getUserFans(UserFansAddParam param){
        if ( param == null ) {
            return null;
        }
        UserFans userFans = this.delegate.getUserFans(param);
        userFans.setCreateTime(System.currentTimeMillis());
        userFans.setUpdateTime(System.currentTimeMillis());
        userFans.setFocusState(0);
        userFans.setIsDel(false);
        return userFans;
    }
}



