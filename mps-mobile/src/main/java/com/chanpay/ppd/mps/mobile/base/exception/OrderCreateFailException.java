package com.chanpay.ppd.mps.mobile.base.exception;

import com.chanpay.ppd.mps.web.base.exception.BusinessException;

/**
 * Created by jiji on 2017/4/27.
 * 订单支付失败
 */
public class OrderCreateFailException extends BusinessException {
    public OrderCreateFailException(String message) {
        super(message);
    }
}
