package com.chanpay.ppd.mps.mobile.entity;

import java.math.BigDecimal;

/**
 * Created by jiji on 2017/5/8.
 */
public class QueryMerPermissionsResponse {

    private String isOpenCash;
    private String isOpenApp;
    private BigDecimal appSrvFee;
    private BigDecimal cashSrvFee;

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
