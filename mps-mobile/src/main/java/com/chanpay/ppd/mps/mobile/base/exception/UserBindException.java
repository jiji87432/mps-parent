package com.chanpay.ppd.mps.mobile.base.exception;

import com.chanpay.ppd.mps.web.base.exception.BusinessException;

/**
 * Created by jiji on 2017/4/27.
 * 用户绑定异常
 */
public class UserBindException extends BusinessException {
    public UserBindException(String message) {
        super(message);
    }
}
