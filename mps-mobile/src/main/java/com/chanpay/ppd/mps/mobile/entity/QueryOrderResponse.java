package com.chanpay.ppd.mps.mobile.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jiji on 2017/4/24.
 */
public class QueryOrderResponse {

    // 子订单号
    private String payOrderNo;

    // 金额
    private BigDecimal amount;

    // 订单来源 WEB：网关收银台  APP：移动收款 POS：订单pos
    private String orderSource;

    // 支付平台交易订单号
    private String tradeOrderNo;

    // 消费用户
    private String buyerName;

    // 商品名称
    private String productName;

    // 订单创建时间
    private Date orderCrtDate;

    // 第三方支付订单号
    private String thirdOrderNo;

    private String remark;

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getTradeOrderNo() {
        return tradeOrderNo;
    }

    public void setTradeOrderNo(String tradeOrderNo) {
        this.tradeOrderNo = tradeOrderNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getOrderCrtDate() {
        return orderCrtDate;
    }

    public void setOrderCrtDate(Date orderCrtDate) {
        this.orderCrtDate = orderCrtDate;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
