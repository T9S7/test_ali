package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.UserAuthenticationAssembler;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationBindParam;
import com.armsmart.jupiter.bs.api.dto.request.UserAuthenticationUpdateParam;
import com.armsmart.jupiter.bs.api.entity.UserAuthentication;

/**
 * UserAuthentication 对象修饰器
 * @author wei.lin
 **/
public abstract class UserAuthenticationDecorator implements UserAuthenticationAssembler {

    private UserAuthenticationAssembler delegate;

    public UserAuthenticationDecorator(UserAuthenticationAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public UserAuthentication getUserAuthentication(UserAuthenticationAddParam param){
        UserAuthentication userAuthentication = delegate.getUserAuthentication(param);
        userAuthentication.setCreateTime(System.currentTimeMillis());
        userAuthentication.setPublicKeyE("");
        userAuthentication.setPublicKeyM("");
        userAuthentication.setUpdateTime(System.currentTimeMillis());
        userAuthentication.setIsDel(false);
        userAuthentication.setIsBind(false);
        userAuthentication.setIsCert(false);
        return userAuthentication;
    }

    @Override
    public UserAuthentication getUserAuthentication(UserAuthenticationUpdateParam param){
        UserAuthentication userAuthentication = delegate.getUserAuthentication(param);
        userAuthentication.setUpdateTime(System.currentTimeMillis());
        userAuthentication.setIsCert(true);
        userAuthentication.setIsBind(true);
        return userAuthentication;
    }

    @Override
    public UserAuthentication getUserAuthentication(UserAuthenticationBindParam param){
        UserAuthentication userAuthentication = delegate.getUserAuthentication(param);
        userAuthentication.setUpdateTime(System.currentTimeMillis());
        userAuthentication.setIsBind(true);
        return userAuthentication;
    }
}



