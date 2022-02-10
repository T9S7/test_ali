package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.ArtPicInfoAssembler;
import com.armsmart.jupiter.bs.api.dto.request.ArtPicInfoAddParam;
import com.armsmart.jupiter.bs.api.entity.ArtPicInfo;

/**
 * ArtPicInfo 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class ArtPicInfoDecorator implements ArtPicInfoAssembler {

    private ArtPicInfoAssembler delegate;

    public ArtPicInfoDecorator(ArtPicInfoAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public ArtPicInfo getArtPicInfo(ArtPicInfoAddParam param) {
        ArtPicInfo artPicInfo = delegate.getArtPicInfo(param);
        artPicInfo.setCreateTime(System.currentTimeMillis());
        artPicInfo.setIsDel(false);
        return artPicInfo;
    }
}



