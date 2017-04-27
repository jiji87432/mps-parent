package com.chanpay.ppd.mps.mobile.base;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jiji on 2017/4/24.
 */
public class BaseRequestMessage implements Serializable {
    // 请求系统编号
    @NotNull(message = "{NotNull.BaseRequestMessage.operSys}")
    @Length(max = 6, message = "{Length.BaseRequestMessage.operSys}")
    public String operSys;

    // 请求系统日期
    @NotNull(message = "{NotNull.BaseRequestMessage.reqSysDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date reqSysDate;

    // 请求流水号
    @NotNull(message = "{NotNull.BaseRequestMessage.flowNo}")
    @Length(max = 26, message = "{Length.BaseRequestMessage.flowNo}")
    public String flowNo;

    // 扩展参数
    public String extParam;

    public String getOperSys() {
        return operSys;
    }

    public void setOperSys(String operSys) {
        this.operSys = operSys;
    }

    public Date getReqSysDate() {
        return reqSysDate;
    }

    public void setReqSysDate(Date reqSysDate) {
        this.reqSysDate = reqSysDate;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }
}
