package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.SysUserAssembler;
import com.armsmart.jupiter.bs.api.dto.request.SysUserAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysUserUpdateParam;
import com.armsmart.jupiter.bs.api.entity.SysUser;

/**
 * SysUser 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class SysUserDecorator implements SysUserAssembler {

    private SysUserAssembler delegate;

    public SysUserDecorator(SysUserAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public SysUser getSysUser(SysUserAddParam param) {
        SysUser sysUser = delegate.getSysUser(param);
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setIsDel(false);
        sysUser.setIsEnable(true);
        sysUser.setNeedChangePwd(true);
        return sysUser;
    }

    @Override
    public SysUser getSysUser(SysUserUpdateParam param) {
        SysUser sysUser = delegate.getSysUser(param);
        sysUser.setUpdateTime(System.currentTimeMillis());
        return sysUser;
    }
}



