package com.armsmart.jupiter.bs.api.security;

import com.armsmart.jupiter.bs.api.entity.UserAuth;
import com.armsmart.jupiter.bs.api.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 管理用户详情
 *
 * @author wel.lin
 */
@Getter
@Setter
public class AppUserDetails implements UserDetails {

    private UserAuth userAuth;
    private UserInfo userInfo;

    public AppUserDetails(UserAuth userAuth, UserInfo userInfo) {
        this.userAuth = userAuth;
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userAuth.getCredential();
    }

    @Override
    public String getUsername() {
        return userAuth.getIdentifier();
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
        return true;
    }
}
