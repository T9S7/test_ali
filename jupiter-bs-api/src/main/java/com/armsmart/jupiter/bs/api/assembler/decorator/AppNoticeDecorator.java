package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.AppNoticeAssembler;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppNoticeUpdateParam;
import com.armsmart.jupiter.bs.api.entity.AppNotice;

/**
 * AppNotice 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class AppNoticeDecorator implements AppNoticeAssembler {

    private AppNoticeAssembler delegate;

    public AppNoticeDecorator(AppNoticeAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public AppNotice getAppNotice(AppNoticeAddParam param) {
        AppNotice appNotice = delegate.getAppNotice(param);
        appNotice.setIsDel(false);
        appNotice.setCreateTime(System.currentTimeMillis());
        return appNotice;
    }

    @Override
    public AppNotice getAppNotice(AppNoticeUpdateParam param) {
        AppNotice appNotice = delegate.getAppNotice(param);
        appNotice.setUpdateTime(System.currentTimeMillis());
        return appNotice;
    }
}



