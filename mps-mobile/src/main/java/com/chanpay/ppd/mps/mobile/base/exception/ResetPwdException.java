package com.chanpay.ppd.mps.mobile.base.exception;

import com.chanpay.ppd.mps.web.base.exception.BusinessException;

/**
 * Created by jiji on 2017/4/27.
 * 重置密码异常
 */
public class ResetPwdException extends BusinessException {
    public ResetPwdException(String message) {
        super(message);
    }
}
