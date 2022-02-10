package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.RefundInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.RefundInfo;
import com.armsmart.jupiter.bs.api.dto.response.RefundInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.RefundInfoUpdateParam;
import java.util.List;

/**
 * RefundInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  RefundInfoDecorator implements RefundInfoAssembler {

    private RefundInfoAssembler delegate;

    public RefundInfoDecorator(RefundInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public RefundInfo getRefundInfo(RefundInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        RefundInfo refundInfo = this.delegate.getRefundInfo(param);
        refundInfo.setCreateTime(System.currentTimeMillis());
        refundInfo.setIsDel(false);
        return refundInfo;
    }
}



