package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.SysResourceAssembler;
import com.armsmart.jupiter.bs.api.dto.request.SysResourceAddParam;
import com.armsmart.jupiter.bs.api.entity.SysResource;

/**
 * SysResource 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class SysResourceDecorator implements SysResourceAssembler {

    private SysResourceAssembler delegate;

    public SysResourceDecorator(SysResourceAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public SysResource getSysResource(SysResourceAddParam param) {
        SysResource resource = delegate.getSysResource(param);
        resource.setCreateTime(System.currentTimeMillis());
        resource.setIsDel(false);
        resource.setIsEnable(true);
        return resource;
    }
}



