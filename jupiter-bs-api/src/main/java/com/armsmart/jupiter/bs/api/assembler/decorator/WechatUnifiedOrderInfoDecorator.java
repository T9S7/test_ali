package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.WechatUnifiedOrderInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.WechatUnifiedOrderInfo;
import com.armsmart.jupiter.bs.api.dto.response.WechatUnifiedOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatUnifiedOrderInfoUpdateParam;
import java.util.List;

/**
 * WechatUnifiedOrderInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  WechatUnifiedOrderInfoDecorator implements WechatUnifiedOrderInfoAssembler {

    private WechatUnifiedOrderInfoAssembler delegate;

    public WechatUnifiedOrderInfoDecorator(WechatUnifiedOrderInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public WechatUnifiedOrderInfo getWechatUnifiedOrderInfo(WechatUnifiedOrderInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        WechatUnifiedOrderInfo wechatUnifiedOrderInfo = this.delegate.getWechatUnifiedOrderInfo(param);
        wechatUnifiedOrderInfo.setCreateTime(System.currentTimeMillis());
        wechatUnifiedOrderInfo.setIsDel(false);
        return wechatUnifiedOrderInfo;
    }
}



