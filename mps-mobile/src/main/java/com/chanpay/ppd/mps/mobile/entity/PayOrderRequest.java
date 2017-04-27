package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.OrderBaseRequestMessage;

import javax.validation.constraints.NotNull;

/**
 * Created by jiji on 2017/4/24.
 */
public class PayOrderRequest extends OrderBaseRequestMessage {

    // 子订单号
    @NotNull(message = "{NotNull.PayOrderRequest.payOrderNo}")
    private String payOrderNo;

    /**
     * W01-微信扫码支付
     * W02-微信被扫支付
     * W03-公众号支付
     * A01-支付宝扫码支付
     * A02-支付宝被扫支付
     * A03-手机网站支付
     * P01-畅捷pos刷卡
     * P02-其他pos刷卡
     * C01-现金
     * Q01-快捷支付
     */
    @NotNull(message = "{NotNull.PayOrderRequest.payTool}")
    private String payTool;

    public PayOrderRequest() {
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getPayTool() {
        return payTool;
    }

    public void setPayTool(String payTool) {
        this.payTool = payTool;
    }

}
