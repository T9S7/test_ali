package com.armsmart.jupiter.bs.api.assembler.decorator;


import com.armsmart.jupiter.bs.api.assembler.UserAddressAssembler;
import com.armsmart.jupiter.bs.api.dto.request.UserAddressAddParam;
import com.armsmart.jupiter.bs.api.entity.UserAddress;

/**
 * PushInfo 对象修饰器
 * @author wei.lin
 **/
public abstract class  UserAddressDecorator implements UserAddressAssembler {

    private UserAddressAssembler delegate;

    public UserAddressDecorator(UserAddressAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public UserAddress getUserAddress(UserAddressAddParam param){
        UserAddress userAddress = delegate.getUserAddress(param);
        userAddress.setCreateTime(System.currentTimeMillis());
        userAddress.setIsDel(false);
        return userAddress;
    }
}
