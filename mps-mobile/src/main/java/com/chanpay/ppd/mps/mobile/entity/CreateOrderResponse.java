package com.chanpay.ppd.mps.mobile.entity;

import java.math.BigDecimal;

/**
 * Created by jiji on 2017/4/24.
 */
public class CreateOrderResponse {

    // 支付平台交易订单号
    private String tradeOrderNo;

    // 支付平台子订单号
    private String payOrderNo;

    // 支付平台子订单号
    private String orderStatus;

    private BigDecimal tradeAmount;

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(BigDecimal tradeAmount) {
        this.tradeAmount = tradeAmount;
    }
}
