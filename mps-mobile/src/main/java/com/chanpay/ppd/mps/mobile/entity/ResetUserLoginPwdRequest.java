package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.BaseRequestMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by jiji on 2017/4/28.
 */
public class ResetUserLoginPwdRequest extends BaseRequestMessage {

    // 登录标识
    @NotNull(message = "{NotNull.User.loginId}")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$", message = "{Pattern.User.loginId}")
    private String loginId;

    // 子登录标识
    @NotNull(message = "{NotNull.UserBindRequest.subLoginId}")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$", message = "{Pattern.UserBindRequest.loginId}")
    private String subLoginId;

    // 商户号
    @NotNull(message = "{NotNull.MerInfo.merId}")
    @Length(max = 32, message = "{Length.MerInfo.merId}")
    private String merId;

    // 登录密码
    @NotNull(message = "{NotNull.User.password}")
    @Length(max = 255, message = "{Length.User.password}")
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getSubLoginId() {
        return subLoginId;
    }

    public void setSubLoginId(String subLoginId) {
        this.subLoginId = subLoginId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
