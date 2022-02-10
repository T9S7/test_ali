package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.PushInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.PushInfo;
import com.armsmart.jupiter.bs.api.dto.response.PushInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.PushInfoUpdateParam;
import java.util.List;

/**
 * PushInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  PushInfoDecorator implements PushInfoAssembler {

    private PushInfoAssembler delegate;

    public PushInfoDecorator(PushInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public PushInfo getPushInfo(PushInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        PushInfo pushInfo = this.delegate.getPushInfo(param);
        pushInfo.setCreateTime(System.currentTimeMillis());
        pushInfo.setIsDel(false);
        return pushInfo;
    }
}



