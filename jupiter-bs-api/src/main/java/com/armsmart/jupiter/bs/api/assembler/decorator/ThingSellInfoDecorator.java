package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.ThingSellInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import com.armsmart.jupiter.bs.api.dto.response.ThingSellInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingSellInfoUpdateParam;
import java.util.List;

/**
 * ThingSellInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  ThingSellInfoDecorator implements ThingSellInfoAssembler {

    private ThingSellInfoAssembler delegate;

    public ThingSellInfoDecorator(ThingSellInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public ThingSellInfo getThingSellInfo(ThingSellInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        ThingSellInfo thingSellInfo = this.delegate.getThingSellInfo(param);
        thingSellInfo.setCreateTime(System.currentTimeMillis());
        thingSellInfo.setUpdateTime(System.currentTimeMillis());
        thingSellInfo.setPutOn(true);
        thingSellInfo.setIsDel(false);
        return thingSellInfo;
    }
}



