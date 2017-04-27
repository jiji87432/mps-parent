package com.chanpay.ppd.mps.mobile.base;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by jiji on 2017/4/24.
 */
public class OrderBaseRequestMessage extends BaseRequestMessage {
    // 合作者商户号
    @NotNull(message = "{NotNull.OrderBaseRequestMessage.partnerId}")
    @Length(max = 32, message = "{Length.OrderBaseRequestMessage.partnerId}")
    private String partnerId;

    // 子商户号
    @NotNull(message = "{NotNull.OrderBaseRequestMessage.merId}")
    @Length(max = 32, message = "{Length.OrderBaseRequestMessage.merId}")
    private String merId;

    // 金额，验证注解的元素值的整数位数和小数位数上限
    @Digits(integer = 13, fraction = 2, message = "{Digits.OrderBaseRequestMessage.tradeAmount}")
    private BigDecimal tradeAmount;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
}
