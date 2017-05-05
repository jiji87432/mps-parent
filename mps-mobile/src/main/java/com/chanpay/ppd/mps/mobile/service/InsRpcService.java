package com.chanpay.ppd.mps.mobile.service;

import com.chanpay.ppd.mps.mobile.base.exception.AuthException;
import com.chanpay.ppd.mps.mobile.base.exception.MerchantBindException;
import com.chanpay.ppd.mps.mobile.entity.UserLoginRequest;

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

    /**
     *
     * @param request
     * @return
     * @throws AuthException
     */
    public void loginAuth(UserLoginRequest request) throws AuthException;
}
