package com.chanpay.ppd.mps.api.exception;


import com.chanpay.ppd.mps.api.exception.base.BusinessException;

/**
 * 用户密码错误
 *
 * @author zhangxd
 */
public class UserPwdErrorException extends BusinessException {

    public UserPwdErrorException(String message) {
        super(message);
    }

}
