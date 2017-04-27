package com.chanpay.ppd.mps.api.service;


import com.chanpay.ppd.mps.api.exception.InvalidCaptchaException;
import com.chanpay.ppd.mps.api.exception.base.BusinessException;

/**
 * 短信服务
 *
 * @author zhangxd
 */
public interface ICaptchaService {

    /**
     * 注册发送验证码
     *
     * @param mobile 手机号
     * @throws BusinessException the business exception
     */
    void sendCaptcha4Registry(String mobile) throws BusinessException;

    /**
     * 忘记密码发送验证码
     *
     * @param mobile 手机号
     * @throws BusinessException the business exception
     */
    void sendCaptcha4Forget(String mobile) throws BusinessException;

    /**
     * 验证验证码是否正确
     *
     * @param mobile  手机号
     * @param captcha 验证码
     * @throws InvalidCaptchaException 验证码错误
     */
    void validCaptcha(String mobile, String captcha) throws BusinessException;

}
