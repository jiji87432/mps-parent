package com.chanpay.ppd.mps.mobile.base.exception;

import com.chanpay.ppd.mps.web.base.exception.BusinessException;

/**
 * Created by jiji on 2017/4/27.
 * 商户绑定异常
 */
public class MerchantBindException extends BusinessException {
    public MerchantBindException(String message) {
        super(message);
    }
}
