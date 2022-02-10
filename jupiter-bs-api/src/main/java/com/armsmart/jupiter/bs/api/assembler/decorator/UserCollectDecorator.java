package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.UserCollectAssembler;

import com.armsmart.jupiter.bs.api.entity.UserCollect;
import com.armsmart.jupiter.bs.api.dto.response.UserCollectDetail;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectAddParam;
import com.armsmart.jupiter.bs.api.dto.request.UserCollectUpdateParam;
import java.util.List;

/**
 * UserCollect 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  UserCollectDecorator implements UserCollectAssembler {

    private UserCollectAssembler delegate;

    public UserCollectDecorator(UserCollectAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public UserCollect getUserCollect(UserCollectAddParam param){
        if ( param == null ) {
            return null;
        }
        UserCollect userCollect = this.delegate.getUserCollect(param);
        userCollect.setCreateTime(System.currentTimeMillis());
        userCollect.setUpdateTime(System.currentTimeMillis());
        userCollect.setIsDel(false);
        return userCollect;
    }
}



