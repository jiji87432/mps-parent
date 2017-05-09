package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.OrderBaseRequestMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by jiji on 2017/5/8.
 */
public class AddMerPermissionsRequest extends OrderBaseRequestMessage {

    // 商户号
    @NotNull(message = "{NotNull.MerInfo.merId}")
    @Length(max = 32, message = "{Length.MerInfo.merId}")
    private String merId;

    // 商户名称
    @NotNull(message = "{NotNull.MerInfo.merName}")
    @Length(max = 32, message = "{Length.MerInfo.merName}")
    private String merName;

    @Override
    public String getMerId() {
        return merId;
    }

    @Override
    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getMerName() {
        return merName;
    }

    public void setMerName(String merName) {
        this.merName = merName;
    }
}
