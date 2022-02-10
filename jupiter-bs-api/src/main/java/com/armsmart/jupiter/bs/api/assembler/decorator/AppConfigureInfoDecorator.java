package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.AppConfigureInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.AppConfigureInfo;
import com.armsmart.jupiter.bs.api.dto.response.AppConfigureInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppConfigureInfoUpdateParam;
import java.util.List;

/**
 * AppConfigureInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  AppConfigureInfoDecorator implements AppConfigureInfoAssembler {

    private AppConfigureInfoAssembler delegate;

    public AppConfigureInfoDecorator(AppConfigureInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public AppConfigureInfo getAppConfigureInfo(AppConfigureInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        AppConfigureInfo appConfigureInfo = this.delegate.getAppConfigureInfo(param);
        appConfigureInfo.setCreateTime(System.currentTimeMillis());
        appConfigureInfo.setIsDel(false);
        return appConfigureInfo;
    }
}



