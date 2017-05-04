package com.chanpay.ppd.mps.mobile.entity.notify;

import java.math.BigDecimal;
import java.util.Date;

public class NotifyMessage {

    /**
     * 通知系统 tss、pos
     */
    private String notifySys;

    /**
     * 通知时间
     */
    private Date notifyTime;

    /**
     * 合作者身份ID（主商户）
     */
    private String partnerId;

    /**
     * 卖家（子商户）
     */
    private String sellerId;

    /**
     * 支付订单号
     */
    private String payOrderNo;

    /**
     * 支付状态 00-待支付 01-支付成功 02-支付中 03：已撤销 99-支付失败
     */
    private String status;

    /**
     * 支付工具 W01-微信扫码支付 W02-微信被扫支付 W03-公众号支付 A01-支付宝扫码支付 A02-支付宝被扫支付 A03-手机网站支付
     * P01-畅捷pos刷卡 P02-其他pos刷卡 C01-现金 Q01-快捷支付
     */
    private String payTool;

    /**
     * 第三方支付订单号
     */
    private String thirdOrderNo;

    /**
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 子订单完成时间
     */
    private Date finishTime;

    /**
     * 登录标识 指店长、管理员或收银员
     */
    private String loginId;

    /**
     * 子订单收款设备 IOS、ANDROID、POS等
     */
    private String paymentDevice;

    /**
     * 支付银行卡号
     */
    private String cardNo;

    /**
     * 卡类别 D：借记 C：贷记
     */
    private String cardType;

    /**
     * 银行编码
     */
    private String bankCode;

    /**
     * 交易终端IP
     */
    private String terminalIp;

    /**
     * 用户行为参数：由15位数字组成的手机电子串号
     */
    private String imei;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    public String getNotifySys() {
        return notifySys;
    }

    public void setNotifySys(String notifySys) {
        this.notifySys = notifySys;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayTool() {
        return payTool;
    }

    public void setPayTool(String payTool) {
        this.payTool = payTool;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPaymentDevice() {
        return paymentDevice;
    }

    public void setPaymentDevice(String paymentDevice) {
        this.paymentDevice = paymentDevice;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}