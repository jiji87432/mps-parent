package com.chanpay.ppd.mps.mobile.base.exception;

import com.chanpay.ppd.mps.web.base.exception.BusinessException;

/**
 * Created by jiji on 2017/4/27.
 * 登录鉴权异常
 */
public class AuthException extends BusinessException {
    public AuthException(String message) {
        super(message);
    }
}
