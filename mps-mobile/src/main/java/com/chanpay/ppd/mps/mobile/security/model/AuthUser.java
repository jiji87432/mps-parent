package com.chanpay.ppd.mps.mobile.security.model;

import com.chanpay.ppd.mps.web.security.model.AbstractAuthUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Security User
 *
 * @author zhangyongji
 */
public class AuthUser extends AbstractAuthUser {

    /**
     * 用户默认角色
     */
    private static final String TRIP_USER_ROLE = "ROLE_USER";
    /**
     * id
     */
    private String id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 冻结
     */
    private boolean enabled;

    public AuthUser(
            String id,
            String mobile,
            String password,
            boolean enabled
    ) {
        this.id = id;
        this.mobile = mobile;
        this.password = password;
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 账户是否冻结
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getUsername() {
        return mobile;
    }

    // 返回分配给用户的角色列表
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(TRIP_USER_ROLE));
        return authorities;
    }

}
