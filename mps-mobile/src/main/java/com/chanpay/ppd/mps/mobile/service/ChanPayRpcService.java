package com.chanpay.ppd.mps.mobile.service;

import com.chanpay.ppd.mps.mobile.base.exception.OrderPayMentFailException;
import com.chanpay.ppd.mps.mobile.entity.PayOrderRequest;
import com.netfinworks.tradeservice.facade.response.PaymentQueryResponse;
import com.netfinworks.tradeservice.facade.response.PaymentResponse;

/**
 * Created by jiji on 2017/4/26.
 */
public interface ChanPayRpcService {

    /**
     *  对订单进行落地并进行支付
     * @param payOrderRequest
     * @return
     */
    public PaymentResponse createAndPay(PayOrderRequest payOrderRequest) throws OrderPayMentFailException;

    /**
     * 根据凭证查询支付订单信息
     *
     * @param tradeVoucherNo
     * @return
     */
    public PaymentQueryResponse queryPaymentResult(String tradeVoucherNo);
}
