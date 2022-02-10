package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.ThingPicInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import com.armsmart.jupiter.bs.api.dto.response.ThingPicInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ThingPicInfoUpdateParam;
import java.util.List;

/**
 * ThingPicInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  ThingPicInfoDecorator implements ThingPicInfoAssembler {

    private ThingPicInfoAssembler delegate;

    public ThingPicInfoDecorator(ThingPicInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public ThingPicInfo getThingPicInfo(ThingPicInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        ThingPicInfo thingPicInfo = this.delegate.getThingPicInfo(param);
        thingPicInfo.setCreateTime(System.currentTimeMillis());
        thingPicInfo.setUpdateTime(System.currentTimeMillis());
        thingPicInfo.setIsDel(false);
        return thingPicInfo;
    }
}



