package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.ArtTypeAssembler;

import com.armsmart.jupiter.bs.api.entity.ArtType;
import com.armsmart.jupiter.bs.api.dto.response.ArtTypeDetail;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ArtTypeUpdateParam;
import java.util.List;

/**
 * ArtType 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  ArtTypeDecorator implements ArtTypeAssembler {

    private ArtTypeAssembler delegate;

    public ArtTypeDecorator(ArtTypeAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public ArtType getArtType(ArtTypeAddParam param){
        if ( param == null ) {
            return null;
        }
        ArtType artType = this.delegate.getArtType(param);
        artType.setCreateTime(System.currentTimeMillis());
        artType.setUpdateTime(System.currentTimeMillis());
        artType.setIsDel(false);
        return artType;
    }
}



