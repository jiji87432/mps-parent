package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.BaseRequestMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by jiji on 2017/4/28.
 */
public class QueryMerInfoRequest extends BaseRequestMessage {

    // 商户号
    @NotNull(message = "{NotNull.MerInfo.merId}")
    @Length(max = 32, message = "{Length.MerInfo.merId}")
    private String merId;

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }
}
