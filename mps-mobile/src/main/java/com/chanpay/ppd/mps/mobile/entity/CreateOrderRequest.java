package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.mps.mobile.base.OrderBaseRequestMessage;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by jiji on 2017/4/24.
 */
public class CreateOrderRequest extends OrderBaseRequestMessage {

    // 商户订单号
    @NotNull(message = "{NotNull.CreateOrderRequest.outTradeNo}")
    @Length(max = 32, message = "{Length.CreateOrderRequest.outTradeNo}")
    private String outTradeNo;

    // 商品名称
    @NotNull(message = "{NotNull.CreateOrderRequest.productName}")
    @Length(max = 256, message = "{Length.CreateOrderRequest.productName}")
    private String productName;

    // 消费用户
    @NotNull(message = "{NotNull.CreateOrderRequest.buyerName}")
    @Length(max = 128, message = "{Length.CreateOrderRequest.buyerName}")
    private String buyerName;

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
    @NotNull(message = "{NotNull.CreateOrderRequest.payTool}")
    @Length(max = 128, message = "{Length.CreateOrderRequest.buyerName}")
    private String payTool;

    // 订单创建开始时间
    @NotNull(message = "{NotNull.CreateOrderRequest.payTool}")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Length(max = 14, message = "{Length.CreateOrderRequest.orderTime}")
    private Date orderTime;

    // 订单有效期
    @NumberFormat
    @Pattern(regexp = "^\\d+[mhd]$",message = "{Pattern.CreateOrderRequest.expiredTime}")
    @Length(max = 128, message = "{Length.CreateOrderRequest.expiredTime}")
    private String expiredTime;

    // 订单来源 WEB：网关收银台  APP：移动收款 POS：订单pos
    @NotNull(message = "{NotNull.CreateOrderRequest.orderSource}")
    @Length(max = 32, message = "{Length.CreateOrderRequest.orderSource}")
    private String orderSource;

    //订单收款设备 OS、ANDROID、POS等
    @Length(max = 32, message = "{Length.CreateOrderRequest.payMentDevice}")
    private String payMentDevice;

    //订单收款设备标识
    @NotNull(message = "{NotNull.CreateOrderRequest.imei}")
    @Length(max = 32, message = "{Length.CreateOrderRequest.imei}")
    private String imei;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
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

    public String getPayTool() {
        return payTool;
    }

    public void setPayTool(String payTool) {
        this.payTool = payTool;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
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

    public String getPayMentDevice() {
        return payMentDevice;
    }

    public void setPayMentDevice(String payMentDevice) {
        this.payMentDevice = payMentDevice;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
