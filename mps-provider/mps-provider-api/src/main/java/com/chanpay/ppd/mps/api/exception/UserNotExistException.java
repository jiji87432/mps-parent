package com.chanpay.ppd.mps.api.exception;


import com.chanpay.ppd.mps.api.exception.base.BusinessException;

/**
 * 用户未存在
 *
 * @author zhangxd
 */
public class UserNotExistException extends BusinessException {

    public UserNotExistException(String message) {
        super(message);
    }

}
