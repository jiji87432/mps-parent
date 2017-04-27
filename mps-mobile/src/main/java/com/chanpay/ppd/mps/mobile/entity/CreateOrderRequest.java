package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.OrderBaseRequestMessage;
import org.hibernate.validator.constraints.Length;

/**
 * Created by jiji on 2017/4/24.
 */
public class CreateOrderRequest extends OrderBaseRequestMessage {

    // 订单状态
    private String orderStatus;

    // 商户订单号
    private String outTradeNo;

    // 支付平台交易订单号
    private String tradeOrderNo;

    // 子订单号
    private String payOrderNo;

    // 商品名称
    private String productName;

    // 消费用户
    private String buyerName;

    // 订单创建开始时间
    @Length(max = 14, message = "{Length.CreateOrderRequest.orderTime}")
    private String orderTime;

    // 订单有效期
    private String expiredTime;

    // 订单来源 WEB：网关收银台  APP：移动收款 POS：订单pos
    private String orderSource;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }
}
