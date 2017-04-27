package com.chanpay.ppd.mps.web.base.exception;

/**
 * 系统业务异常.
 *
 * @author zhangyongji
 */
public class SystemException extends RuntimeException {

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}