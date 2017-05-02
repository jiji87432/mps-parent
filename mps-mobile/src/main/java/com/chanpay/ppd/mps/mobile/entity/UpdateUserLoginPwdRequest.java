package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.BaseRequestMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by jiji on 2017/4/28.
 */
public class UpdateUserLoginPwdRequest extends BaseRequestMessage {

    // 登录标识
    @NotNull(message = "{NotNull.User.loginId}")
    @Pattern(regexp = "^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$", message = "{Pattern.User.loginId}")
    private String loginId;

    // 登录标识类型
    @NotNull(message = "{NotNull.User.idType}")
    @Pattern(regexp = "^01|02$", message = "{Pattern.User.idType}")
    private String idType;

    // 原登录密码
    @NotNull(message = "{NotNull.UpdateUserLoginPwdRequest.oldPwd}")
    @Length(max = 255, message = "{Length.User.password}")
    private String oldPwd;

    // 新登录密码
    @NotNull(message = "{NotNull.UpdateUserLoginPwdRequest.newPwd}")
    @Length(max = 255, message = "{Length.User.password}")
    private String newPwd;

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

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
