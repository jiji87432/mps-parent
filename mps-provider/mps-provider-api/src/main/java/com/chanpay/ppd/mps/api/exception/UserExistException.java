package com.chanpay.ppd.mps.api.exception;


import com.chanpay.ppd.mps.api.exception.base.BusinessException;

/**
 * 用户已存在
 *
 * @author zhangyongji
 */
public class UserExistException extends BusinessException {

    public UserExistException(String message) {
        super(message);
    }

}
