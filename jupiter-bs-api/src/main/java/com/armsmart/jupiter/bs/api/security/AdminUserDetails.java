package com.armsmart.jupiter.bs.api.security;

import com.armsmart.jupiter.bs.api.dto.response.SysResourceDetail;
import com.armsmart.jupiter.bs.api.entity.SysUser;
import com.armsmart.jupiter.bs.api.entity.SysUserAuth;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理用户详情
 *
 * @author wel.lin
 */
@Getter
@Setter
public class AdminUserDetails implements UserDetails {
    private SysUser sysUser;
    private SysUserAuth sysUserAuth;
    private List<SysResourceDetail> resourceList;

    public AdminUserDetails(SysUser sysUser, SysUserAuth sysUserAuth, List<SysResourceDetail> resourceList) {
        this.sysUser = sysUser;
        this.sysUserAuth = sysUserAuth;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return resourceList.stream()
                .map(resource -> new SimpleGrantedAuthority(resource.getResourceId() + ":" + resource.getResourceKey()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return sysUserAuth.getCredential();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getIsEnable();
    }
}
