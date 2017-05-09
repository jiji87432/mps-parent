package com.chanpay.ppd.mps.mobile.base.exception;

import com.chanpay.ppd.mps.web.base.exception.BusinessException;

/**
 * Created by jiji on 2017/4/27.
 * 用户不存在
 */
public class UserNotExistException extends BusinessException {
    public UserNotExistException(String message) {
        super(message);
    }
}
