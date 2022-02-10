package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.CheckInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.CheckInfo;
import com.armsmart.jupiter.bs.api.dto.response.CheckInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.CheckInfoUpdateParam;
import java.util.List;

/**
 * CheckInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  CheckInfoDecorator implements CheckInfoAssembler {

    private CheckInfoAssembler delegate;

    public CheckInfoDecorator(CheckInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public CheckInfo getCheckInfo(CheckInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        CheckInfo checkInfo = this.delegate.getCheckInfo(param);
        checkInfo.setCreateTime(System.currentTimeMillis());
        checkInfo.setIsDel(false);
        return checkInfo;
    }
}



