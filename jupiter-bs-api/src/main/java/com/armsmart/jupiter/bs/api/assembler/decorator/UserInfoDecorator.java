package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.UserInfoAssembler;
import com.armsmart.jupiter.bs.api.dto.request.UserInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserInfoUpdateParam;
import com.armsmart.jupiter.bs.api.entity.UserInfo;

/**
 * UserInfo 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class UserInfoDecorator implements UserInfoAssembler {

    private UserInfoAssembler delegate;

    public UserInfoDecorator(UserInfoAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public UserInfo getUserInfo(UserInfoAddParam param) {
        UserInfo userInfo = delegate.getUserInfo(param);
        return userInfo;
    }

    @Override
    public UserInfo getUserInfo(UserInfoUpdateParam param) {
        UserInfo userInfo = delegate.getUserInfo(param);
        userInfo.setUpdateTime(System.currentTimeMillis());
        return userInfo;
    }
}



