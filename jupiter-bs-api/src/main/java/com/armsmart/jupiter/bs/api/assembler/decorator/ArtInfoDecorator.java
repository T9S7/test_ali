package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.ArtInfoAssembler;
import com.armsmart.jupiter.bs.api.dto.request.ArtInfoAddParam;
import com.armsmart.jupiter.bs.api.entity.ArtInfo;

/**
 * ArtInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class ArtInfoDecorator implements ArtInfoAssembler {

    private ArtInfoAssembler delegate;

    public ArtInfoDecorator(ArtInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public ArtInfo getArtInfo(ArtInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        ArtInfo artInfo = this.delegate.getArtInfo(param);
        artInfo.setCreateTime(System.currentTimeMillis());
        artInfo.setIsDel(false);
        return artInfo;
    }
}



