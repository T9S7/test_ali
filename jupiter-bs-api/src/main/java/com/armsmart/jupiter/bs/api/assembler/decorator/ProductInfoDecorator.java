package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.ProductInfoAssembler;

import com.armsmart.jupiter.bs.api.dto.request.ProductInfoPutOnParam;
import com.armsmart.jupiter.bs.api.entity.ProductInfo;
import com.armsmart.jupiter.bs.api.dto.response.ProductInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.ProductInfoUpdateParam;
import java.util.List;

/**
 * ProductInfo 对象修饰器
 * @author 林伟
 **/
public abstract class  ProductInfoDecorator implements ProductInfoAssembler {

    private ProductInfoAssembler delegate;

    public ProductInfoDecorator(ProductInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public ProductInfo getProductInfo(ProductInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        ProductInfo productInfo = this.delegate.getProductInfo(param);
        productInfo.setCreateTime(System.currentTimeMillis());
        productInfo.setIsDel(false);
        return productInfo;
    }

    @Override
    public ProductInfo getProductInfo(ProductInfoPutOnParam param) {
        ProductInfo productInfo = this.delegate.getProductInfo(param);
        productInfo.setCreateTime(System.currentTimeMillis());
        productInfo.setIsDel(false);
        productInfo.setPutOn(false);
        productInfo.setAuditStatus(1);
        return productInfo;
    }
}



