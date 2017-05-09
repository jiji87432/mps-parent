package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.OrderBaseRequestMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by jiji on 2017/5/8.
 */
public class QueryMerPermissionsRequest extends OrderBaseRequestMessage {

    // 商户号
    @NotNull(message = "{NotNull.MerInfo.merId}")
    @Length(max = 32, message = "{Length.MerInfo.merId}")
    private String merId;

    @Override
    public String getMerId() {
        return merId;
    }

    @Override
    public void setMerId(String merId) {
        this.merId = merId;
    }
}
