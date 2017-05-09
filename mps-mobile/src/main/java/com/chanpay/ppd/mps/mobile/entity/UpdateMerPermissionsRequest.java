package com.chanpay.ppd.mps.mobile.entity;

import com.chanpay.ppd.ins.common.validator.ParamValue;
import com.chanpay.ppd.mps.mobile.base.OrderBaseRequestMessage;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by jiji on 2017/5/8.
 */
public class UpdateMerPermissionsRequest extends OrderBaseRequestMessage {

    // 商户号
    @NotNull(message = "{NotNull.MerInfo.merId}")
    @Length(max = 32, message = "{Length.MerInfo.merId}")
    private String merId;

    @ParamValue("IS_NO")
    private String isOpenCash;

    @ParamValue("IS_NO")
    private String isOpenApp;

    private BigDecimal appSrvFee;

    private BigDecimal cashSrvFee;

    @Override
    public String getMerId() {
        return merId;
    }

    @Override
    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getIsOpenCash() {
        return isOpenCash;
    }

    public void setIsOpenCash(String isOpenCash) {
        this.isOpenCash = isOpenCash;
    }

    public String getIsOpenApp() {
        return isOpenApp;
    }

    public void setIsOpenApp(String isOpenApp) {
        this.isOpenApp = isOpenApp;
    }

    public BigDecimal getAppSrvFee() {
        return appSrvFee;
    }

    public void setAppSrvFee(BigDecimal appSrvFee) {
        this.appSrvFee = appSrvFee;
    }

    public BigDecimal getCashSrvFee() {
        return cashSrvFee;
    }

    public void setCashSrvFee(BigDecimal cashSrvFee) {
        this.cashSrvFee = cashSrvFee;
    }
}
