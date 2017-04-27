package com.chanpay.ppd.mps.api.exception;


import com.chanpay.ppd.mps.api.exception.base.BusinessException;

/**
 * 短信发送太频繁
 *
 * @author zhangxd
 */
public class SmsTooMuchException extends BusinessException {

    public SmsTooMuchException(String message) {
        super(message);
    }

}
