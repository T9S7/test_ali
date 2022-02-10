package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.UserAuthAssembler;

/**
 * UserAuth 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class UserAuthDecorator implements UserAuthAssembler {

    private UserAuthAssembler delegate;

    public UserAuthDecorator(UserAuthAssembler delegate) {
        this.delegate = delegate;
    }

}



