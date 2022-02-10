package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.WechatOrderInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.WechatOrderInfo;
import com.armsmart.jupiter.bs.api.dto.response.WechatOrderInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.WechatOrderInfoUpdateParam;
import java.util.List;

/**
 * WechatOrderInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  WechatOrderInfoDecorator implements WechatOrderInfoAssembler {

    private WechatOrderInfoAssembler delegate;

    public WechatOrderInfoDecorator(WechatOrderInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public WechatOrderInfo getWechatOrderInfo(WechatOrderInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        WechatOrderInfo wechatOrderInfo = this.delegate.getWechatOrderInfo(param);
        wechatOrderInfo.setCreateTime(System.currentTimeMillis());
        wechatOrderInfo.setIsDel(false);
        return wechatOrderInfo;
    }
}



