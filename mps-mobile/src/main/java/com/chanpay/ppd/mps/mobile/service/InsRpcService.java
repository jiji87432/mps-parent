package com.chanpay.ppd.mps.mobile.service;

import com.chanpay.ppd.mps.mobile.base.exception.MerchantBindException;

/**
 * Created by jiji on 2017/4/26.
 */
public interface InsRpcService {
    /**
     *
     * @param partnerId
     * @param merId
     * @return
     */
    public void validateMerNo(String partnerId, String merId) throws MerchantBindException;
}
