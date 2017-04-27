package com.chanpay.ppd.mps.mobile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chanpay.ppd.ins.api.mch.facade.IMchBizFacade;
import com.chanpay.ppd.ins.api.mch.facade.dto.QueryMchRequest;
import com.chanpay.ppd.ins.api.mch.facade.dto.QueryMchResponse;
import com.chanpay.ppd.mps.mobile.base.exception.MerchantBindException;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
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
    private WebServiceHelper webServiceHelper;

    @Override
    public void validateMerNo(String partnerId, String merId) throws MerchantBindException {
        boolean flag = false;
        QueryMchRequest request = new QueryMchRequest();
        webServiceHelper.requestWrapper(request);
        request.setMainMchId(partnerId);
        request.setSubMchId(merId);
        LOGGER.info("调用DUBBO查询商户绑定关系请求对象：", JSONObject.toJSONString(request));
        QueryMchResponse response = mchBizFacade.queryMch(request);
        if (null != response) {
            LOGGER.info("调用DUBBO查询商户绑定关系响应对象：", JSONObject.toJSONString(response));
            if (response.getRespCode().equals("INS00000") && response.getMainSubMchList() != null && response.getMainSubMchList().size() > 0) {
                flag = true;
            } else {
                throw new MerchantBindException(String.format("商户 '%s' 和 '%s' 绑定关系不存在", partnerId, merId));
            }
        }
    }
}
