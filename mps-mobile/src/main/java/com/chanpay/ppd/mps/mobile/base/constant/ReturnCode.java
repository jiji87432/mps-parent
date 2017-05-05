package com.chanpay.ppd.mps.mobile.base.constant;

/**
 * 返回 code 常量
 *
 * @author zhangyongji
 */
public final class ReturnCode {

    /**
     * 成功
     */
    public static final String SUCCESS = "00000";
    public static final String SUCCESS_DESC = "SUCCESS";


    /**
     * 400 (错误请求) 服务器不理解请求的语法。
     */
    public static final String BAD_REQUEST = "10001";
    /**
     * 404 (未找到) 服务器找不到请求的资源。
     */
    public static final String NOT_FOUND = "10002";
    /**
     * 405 (方法禁用) 禁用请求中指定的方法。
     */
    public static final String METHOD_NOT_ALLOWED = "10003";
    /**
     * 406 (不接受) 无法使用请求的内容特性响应请求的网页。
     */
    public static final String NOT_ACCEPTABLE = "10004";
    /**
     * 415 (不支持的媒体类型) 请求的格式不受请求页面的支持。
     */
    public static final String UNSUPPORTED_MEDIA_TYPE = "10005";
    /**
     * 500 (服务器内部错误) 服务器遇到错误，无法完成请求。
     */
    public static final String INTERNAL_SERVER_ERROR = "10006";
    /**
     * 401 (未授权) 请求要求身份验证。 (Basic 认证错误或无权限头)
     */
    public static final String UNAUTHORIZED = "10007";
    /**
     * 403 (禁止) 服务器拒绝请求。
     */
    public static final String FORBIDDEN = "10008";


    /**
     * 400 字段校验错误
     */
    public static final String INVALID_FIELD = "20001";


    /**
     * 400 用户名,密码错误
     */
    public static final String INVALID_GRANT = "20002";

    /**
     * 403 用户被冻结
     */
    public static final String DISABLED_USER = "20003";


    /**
     * 400 用户已存在
     */
    public static final String USER_EXIST = "20004";
    /**
     * 400 用户不存在
     */
    public static final String USER_NOT_EXIST = "20005";
    /**
     * 403 短信发送太频繁
     */
    public static final String SMS_TOO_MUCH = "20006";


    /**
     * 400 无效验证码
     */
    public static final String INVALID_CAPTCHA = "20007";

    /**
     * 400 订单支付失败
     */
    public static final String ORDER_PAYMENT_FAIL = "20008";


    /**
     * 400 主子商户校验
     */
    public static final String INVALID_MERCHANT_BIND = "20009";

    /**
     * 400 订单创建失败
     */
    public static final String ORDER_CREATE_FAIL = "20010";

    /**
     * 400 用户绑定关系已存在
     */
    public static final String USER_BIND_EXIST = "20011";

    /**
     * 400 重置登录密码失败
     */
    public static final String RESET_PWD_FAIL = "20012";

    /**
     * 400 修改登录密码失败
     */
    public static final String UPDATE_PWD_FAIL = "20013";

    /**
     * 400 登录鉴权失败
     */
    public static final String AUTH_FAIL = "20014";


    private ReturnCode() {
        throw new IllegalAccessError("Utility class");
    }
}
