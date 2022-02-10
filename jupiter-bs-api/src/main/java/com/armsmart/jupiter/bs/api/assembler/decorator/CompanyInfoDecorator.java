package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.CompanyInfoAssembler;

import com.armsmart.jupiter.bs.api.entity.CompanyInfo;
import com.armsmart.jupiter.bs.api.dto.response.CompanyInfoDetail;
import com.armsmart.jupiter.bs.api.dto.request.CompanyInfoAddParam;
import com.armsmart.jupiter.bs.api.dto.request.CompanyInfoUpdateParam;
import java.util.List;

/**
 * CompanyInfo 对象修饰器
 * @author 苏礼刚
 **/
public abstract class  CompanyInfoDecorator implements CompanyInfoAssembler {

    private CompanyInfoAssembler delegate;

    public CompanyInfoDecorator(CompanyInfoAssembler delegate){
        this.delegate = delegate;
    }

    @Override
    public CompanyInfo getCompanyInfo(CompanyInfoAddParam param){
        if ( param == null ) {
            return null;
        }
        CompanyInfo companyInfo = this.delegate.getCompanyInfo(param);
        companyInfo.setCreateTime(System.currentTimeMillis());
        companyInfo.setUpdateTime(System.currentTimeMillis());
        companyInfo.setIsDel(false);
        return companyInfo;
    }
}



