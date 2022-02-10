package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.SmsInfoAssembler;

/**
 * SmsInfo 对象修饰器
 * @author wei.lin
 **/
public abstract class SmsInfoDecorator implements SmsInfoAssembler {

    private SmsInfoAssembler delegate;

    public SmsInfoDecorator(SmsInfoAssembler delegate){
        this.delegate = delegate;
    }

}



