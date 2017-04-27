package com.chanpay.ppd.mps.mobile.service;

import com.chanpay.ppd.mps.mobile.base.exception.OrderPayMentFailException;
import com.chanpay.ppd.mps.mobile.entity.PayOrderRequest;
import com.netfinworks.tradeservice.facade.response.PaymentResponse;

/**
 * Created by jiji on 2017/4/26.
 */
public interface ChanPayRpcService {

    /**
     *
     * @param payOrderRequest
     * @return
     */
    public PaymentResponse createAndPay(PayOrderRequest payOrderRequest) throws OrderPayMentFailException;
}
