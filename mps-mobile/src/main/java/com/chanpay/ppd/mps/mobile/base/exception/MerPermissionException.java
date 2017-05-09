package com.chanpay.ppd.mps.mobile.base.exception;

import com.chanpay.ppd.mps.web.base.exception.BusinessException;

/**
 * Created by jiji on 2017/4/27.
 * 商户权限异常
 */
public class MerPermissionException extends BusinessException {
    public MerPermissionException(String message) {
        super(message);
    }
}
