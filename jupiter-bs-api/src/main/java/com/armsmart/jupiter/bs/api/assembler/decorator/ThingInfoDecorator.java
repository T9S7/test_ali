package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.ThingInfoAssembler;
import com.armsmart.jupiter.bs.api.constants.ThingState;
import com.armsmart.jupiter.bs.api.dto.request.ThingInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingNfcUpdateParam;
import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import org.springframework.util.StringUtils;

/**
 * ThingInfo 对象修饰器
 *
 * @author 苏礼刚
 **/
public abstract class ThingInfoDecorator implements ThingInfoAssembler {

    private ThingInfoAssembler delegate;

    public ThingInfoDecorator(ThingInfoAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public ThingInfo getThingInfo(ThingInfoAddParam param) {
        if (param == null) {
            return null;
        }
        ThingInfo thingInfo = this.delegate.getThingInfo(param);
        thingInfo.setCreateTime(System.currentTimeMillis());
        thingInfo.setIsOfficial(false);
        thingInfo.setIsDel(false);
        String unknown = "未知";
        if (!StringUtils.hasText(thingInfo.getThingSize())) {
            thingInfo.setThingSize(unknown);
        }
        if (!StringUtils.hasText(thingInfo.getThingCondition())) {
            thingInfo.setThingCondition(unknown);
        }
        if (!StringUtils.hasText(thingInfo.getThingElement())) {
            thingInfo.setThingElement(unknown);
        }
        return thingInfo;
    }

    @Override
    public ThingInfo getThingInfo(ThingNfcAddParam param) {
        ThingInfo thingInfo = this.delegate.getThingInfo(param);
        thingInfo.setCreateTime(System.currentTimeMillis());
        thingInfo.setIsDel(false);
        thingInfo.setIsOfficial(true);
        thingInfo.setUploadStatus(true);
        thingInfo.setCurrentState(ThingState.PUT_OFF.getCode());
        thingInfo.setContractAddr("0x0000000000000000000000000000000000000000");
        return thingInfo;
    }

    @Override
    public ThingInfo getThingInfo(ThingNfcUpdateParam param) {
        ThingInfo thingInfo = this.delegate.getThingInfo(param);
        thingInfo.setUpdateTime(System.currentTimeMillis());
        return thingInfo;
    }
}



