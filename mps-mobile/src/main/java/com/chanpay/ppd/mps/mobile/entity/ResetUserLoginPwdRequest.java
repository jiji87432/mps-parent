package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.BaseRequestMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by jiji on 2017/4/28.
 */
public class ResetUserLoginPwdRequest extends BaseRequestMessage {

    // 登录标识
    @NotNull(message = "NotNull.UserLoginRequest.loginId")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$")
    private String loginId;

    // 登录标识类型
    @NotNull(message = "NotNull.UserLoginRequest.idType")
    private String idType;

    // 登录密码
    @NotNull(message = "NotNull.UserLoginRequest.password")
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}