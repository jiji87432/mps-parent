package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.BaseRequestMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by jiji on 2017/4/28.
 */
public class QueryUserInfoRequest extends BaseRequestMessage {

    // 登录标识
    @NotNull(message = "{NotNull.User.loginId}")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$", message = "{Pattern.User.loginId}")
    private String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

}
