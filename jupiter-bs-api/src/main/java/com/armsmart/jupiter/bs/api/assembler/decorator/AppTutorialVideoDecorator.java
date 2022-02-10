package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.AppTutorialVideoAssembler;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.AppTutorialVideoUpdateParam;
import com.armsmart.jupiter.bs.api.entity.AppTutorialVideo;

/**
 * AppTutorialVideo 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class AppTutorialVideoDecorator implements AppTutorialVideoAssembler {

    private AppTutorialVideoAssembler delegate;

    public AppTutorialVideoDecorator(AppTutorialVideoAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public AppTutorialVideo getAppTutorialVideo(AppTutorialVideoAddParam param) {
        AppTutorialVideo appTutorialVideo = delegate.getAppTutorialVideo(param);
        appTutorialVideo.setCreateTime(System.currentTimeMillis());
        appTutorialVideo.setIsDel(false);
        return appTutorialVideo;
    }

    @Override
    public AppTutorialVideo getAppTutorialVideo(AppTutorialVideoUpdateParam param) {
        AppTutorialVideo appTutorialVideo = delegate.getAppTutorialVideo(param);
        appTutorialVideo.setUpdateTime(System.currentTimeMillis());
        return appTutorialVideo;
    }
}



