package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.AppVersionAssembler;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppVersionUpdateParam;
import com.armsmart.jupiter.bs.api.entity.AppVersion;


/**
 * AppVersion 对象修饰器
 *
 * @author 苏礼刚
 **/
public abstract class AppVersionDecorator implements AppVersionAssembler {

    private AppVersionAssembler delegate;

    public AppVersionDecorator(AppVersionAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public AppVersion getAppVersion(AppVersionAddParam param) {
        if (param == null) {
            return null;
        }
        AppVersion appVersion = this.delegate.getAppVersion(param);
        appVersion.setCreateTime(System.currentTimeMillis());
        appVersion.setUpdateTime(System.currentTimeMillis());
        appVersion.setIsDel(false);
        return appVersion;
    }

    @Override
    public AppVersion getAppVersion(AppVersionUpdateParam param) {
        AppVersion appVersion = this.delegate.getAppVersion(param);
        appVersion.setUpdateTime(System.currentTimeMillis());
        return appVersion;
    }
}



