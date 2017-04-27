package com.chanpay.ppd.mps.mobile.base.constant;

/**
 * <b>Description:</b>参数常量值定义<br/>
 * <b>CreateTime:</b> 2017年4月20日 上午11:34:20<br/>
 *
 * @author zhangyongji
 * @since 1.0.0
 */
public interface ParamConstants {

    public static final String OPER_SYS_CODE = "MPS";

    public static interface ProductCode {
        // 即时到帐交易
        public static final String INSTANT_PAY = "20201";
    }

    /**
     * 支付渠道   23(网银借记卡) 28（微信） 29(支付宝)
     */
    public static interface PayChannel {
        public static final String NETBANK = "23";
        public static final String WXPAY = "28";
        public static final String ALIPAY = "29";
    }

    /**
     * //WXPAY 微信 	ALIPAY 支付宝
     */
    public static interface BankCode {
        public static final String WXPAY = "WXPAY";
        public static final String ALIPAY = "ALIPAY";
    }

    /**
     * SUCCESS("S0001", "调用成功"),
     * FAILED("F0001", "调用失败"),
     * PARAM_NOT_VALIDATE( "F0002","参数不合法"),
     * TRADE_CLOSED( "F0003","交易关闭"),
     * PAYMENT_DUPLIDATE( "F0004","重复支付"),
     * TRADE_AMOUNT_MODIFIED( "F0005","交易金额修改"),
     * TRADE_FINISHED("F0007","交易结束"),
     * LFLT_LIMITED("F0008","限额限次未通过"),
     */
    public static interface TradeService {
        public static final String SUCCESS = "S0001";
    }

}
