package com.armsmart.jupiter.bs.api.assembler.decorator;


import com.armsmart.jupiter.bs.api.assembler.SysRegionAssembler;

/**
 * SysRegion 对象修饰器
 * @author 苏礼刚
 **/
public abstract class SysRegionDecorator implements SysRegionAssembler {

    private SysRegionAssembler delegate;

    public SysRegionDecorator(SysRegionAssembler delegate){
        this.delegate = delegate;
    }

}



