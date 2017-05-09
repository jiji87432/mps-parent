package com.chanpay.ppd.mps.mobile.common.controller;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.chanpay.ppd.mps.common.utils.BeanValidators;
import com.chanpay.ppd.mps.common.utils.Collections3;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.web.base.exception.BusinessException;
import com.chanpay.ppd.mps.web.editor.DateEditor;
import com.chanpay.ppd.mps.web.editor.StringEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器支持类
 *
 * @author zhangyongji
 */
public abstract class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     *
     * @param binder the binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new StringEditor());
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    /**
     * Handle business exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleBusinessException(BusinessException ex) {
        LOGGER.debug("handle handleBusinessException");
        return makeErrorMessage(ReturnCode.INTERNAL_SERVER_ERROR, "Business Error", ex.getMessage());
    }

    /**
     * Handle constraint violation exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
        LOGGER.debug("handle ConstraintViolationException" + ex);
        List<String> list = BeanValidators.extractMessage(ex);
        return makeErrorMessage(ReturnCode.INVALID_FIELD,
                "Invalid Field", Collections3.convertToString(list, ";"));
    }

    /**
     * Make error message map.
     *
     * @param respCode
     * @param errorMsg
     * @param respCodeDesc
     * @return
     */
    protected Map<String, Object> makeErrorMessage(String respCode, String errorMsg, String respCodeDesc) {
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, respCode);
        message.put(BaseResponeMessage.ERROR_MESSAGE, errorMsg);
        message.put(BaseResponeMessage.RESP_CODE_DESC, respCodeDesc);
        return message;
    }

}
