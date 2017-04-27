package com.chanpay.ppd.mps.api.exception.base;

/**
 * 业务异常.
 *
 * @author zhangyongji
 */
public class BusinessException extends Exception {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

}