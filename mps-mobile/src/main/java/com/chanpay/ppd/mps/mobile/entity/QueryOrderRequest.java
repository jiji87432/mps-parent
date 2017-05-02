package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.OrderBaseRequestMessage;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by jiji on 2017/4/24.
 */
public class QueryOrderRequest extends OrderBaseRequestMessage {

    // 订单状态
    private String orderStatus;

    // 商户订单号
    private String outTradeNo;

    // 支付平台交易订单号
    private String tradeOrderNo;

    // 子订单号
    private String payOrderNo;

    // 消费用户
    private String buyerName;

    // 订单创建开始时间
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private Date orderStartDate;

    // 订单创建结束时间
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private Date orderEndDate;

    public QueryOrderRequest() {
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Date getOrderStartDate() {
        return orderStartDate;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public Date getOrderEndDate() {
        return orderEndDate;
    }

    public void setOrderEndDate(Date orderEndDate) {
        this.orderEndDate = orderEndDate;
    }
}
