package com.armsmart.jupiter.bs.api.assembler.decorator;

import com.armsmart.jupiter.bs.api.assembler.SysRoleAssembler;

import com.armsmart.jupiter.bs.api.entity.SysRole;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleAddParam;
import com.armsmart.jupiter.bs.api.dto.request.SysRoleUpdateParam;

/**
 * SysRole 对象修饰器
 *
 * @author wei.lin
 **/
public abstract class SysRoleDecorator implements SysRoleAssembler {

    private SysRoleAssembler delegate;

    public SysRoleDecorator(SysRoleAssembler delegate) {
        this.delegate = delegate;
    }

    @Override
    public SysRole getSysRole(SysRoleAddParam param) {
        SysRole sysRole = delegate.getSysRole(param);
        sysRole.setCreateTime(System.currentTimeMillis());
        sysRole.setIsDel(false);
        sysRole.setIsEnable(true);
        sysRole.setRoleLevel(sysRole.getRoleType());
        return sysRole;
    }

    @Override
    public SysRole getSysRole(SysRoleUpdateParam param) {
        SysRole sysRole = delegate.getSysRole(param);
        sysRole.setUpdateTime(System.currentTimeMillis());
        sysRole.setRoleLevel(sysRole.getRoleType());
        return sysRole;
    }
}



