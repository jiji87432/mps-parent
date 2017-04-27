package com.chanpay.ppd.mps.mobile.base;

import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import org.apache.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据常量
 *
 * @author zhangyongji
 */
public final class BaseResponeMessage {

    /**
     * 状态
     */
    public static final String RESP_CODE = "respcode";
    /**
     * 错误信息
     */
    public static final String ERROR_MESSAGE = "errorMsg";
    /**
     * 错误描述
     */
    public static final String RESP_CODE_DESC = "respCodeDesc";
    /**
     * 返回数据
     */
    public static final String RESP_DATA = "data";

    /**
     * HTTP状态码与系统错误代码对应关系
     */
    public static final Map<Integer, String> STATUS_CODE_MAP;

    static {
        Map<Integer, String> codeMappingMap = new HashMap<>();
        codeMappingMap.put(HttpStatus.SC_INTERNAL_SERVER_ERROR, ReturnCode.INTERNAL_SERVER_ERROR);
        codeMappingMap.put(HttpStatus.SC_METHOD_NOT_ALLOWED, ReturnCode.METHOD_NOT_ALLOWED);
        codeMappingMap.put(HttpStatus.SC_BAD_REQUEST, ReturnCode.BAD_REQUEST);
        codeMappingMap.put(HttpStatus.SC_NOT_FOUND, ReturnCode.NOT_FOUND);
        codeMappingMap.put(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, ReturnCode.UNSUPPORTED_MEDIA_TYPE);
        codeMappingMap.put(HttpStatus.SC_NOT_ACCEPTABLE, ReturnCode.NOT_ACCEPTABLE);
        codeMappingMap.put(HttpStatus.SC_UNAUTHORIZED, ReturnCode.UNAUTHORIZED);
        codeMappingMap.put(HttpStatus.SC_FORBIDDEN, ReturnCode.FORBIDDEN);

        STATUS_CODE_MAP = Collections.unmodifiableMap(codeMappingMap);
    }

    private BaseResponeMessage() {
        throw new IllegalAccessError("Utility class");
    }

    public static String getRespCode() {
        return RESP_CODE;
    }

    public static String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    public static String getRespCodeDesc() {
        return RESP_CODE_DESC;
    }
}
