package com.chanpay.ppd.mps.mobile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chanpay.ppd.ins.api.mch.facade.IMchBizFacade;
import com.chanpay.ppd.ins.api.mch.facade.dto.QueryMchRequest;
import com.chanpay.ppd.ins.api.mch.facade.dto.QueryMchResponse;
import com.chanpay.ppd.ins.api.security.facade.IAuthFacade;
import com.chanpay.ppd.ins.api.security.facade.dto.LoginAuthRequest;
import com.chanpay.ppd.ins.api.security.facade.dto.LoginAuthResponse;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.base.exception.AuthException;
import com.chanpay.ppd.mps.mobile.base.exception.MerchantBindException;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.UserLoginRequest;
import com.chanpay.ppd.mps.mobile.service.InsRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by jiji on 2017/4/26.
 */
@Controller
public class InsRpcServiceImpl implements InsRpcService {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InsRpcServiceImpl.class);

    @Autowired
    private IMchBizFacade mchBizFacade;
    @Autowired
    private IAuthFacade authFacade;
    @Autowired
    private WebServiceHelper webServiceHelper;

    @Override
    public void validateMerNo(String partnerId, String merId) throws MerchantBindException {
        QueryMchRequest request = new QueryMchRequest();
        webServiceHelper.requestWrapper(request);
        request.setMainMchId(partnerId);
        request.setSubMchId(merId);
        LOGGER.info("调用DUBBO查询商户绑定关系请求对象：", JSONObject.toJSONString(request));
        QueryMchResponse response = mchBizFacade.queryMch(request);
        if (null != response) {
            LOGGER.info("调用DUBBO查询商户绑定关系响应对象：", JSONObject.toJSONString(response));
            if (response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS) && response.getMainSubMchList() != null && response.getMainSubMchList().size() > 0) {
                //nothing to do
            } else {
                throw new MerchantBindException(String.format("商户 '%s' 和 '%s' 绑定关系不存在", partnerId, merId));
            }
        }
    }

    @Override
    public void loginAuth(UserLoginRequest request) throws AuthException {
        LoginAuthRequest loginAuthRequest = new LoginAuthRequest();
        webServiceHelper.requestWrapper(request);
        loginAuthRequest.setLoginId(request.getLoginId());
        loginAuthRequest.setIdType(request.getIdType());
        loginAuthRequest.setLoginPwd(request.getPassword());
        LoginAuthResponse response = authFacade.loginAuth(loginAuthRequest);
        LOGGER.info("调用登录鉴权响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && !response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            throw new AuthException(String.format("用户 '%s' 登录鉴权失败", request.getLoginId()));
        }
    }
}
