package com.chanpay.ppd.mps.mobile.security;

import com.alibaba.fastjson.JSONObject;
import com.chanpay.ppd.ins.api.mch.facade.IMchUserFacade;
import com.chanpay.ppd.ins.api.mch.facade.dto.QueryMchUserRequest;
import com.chanpay.ppd.ins.api.mch.facade.dto.QueryMchUserResponse;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.user.User;
import com.chanpay.ppd.mps.mobile.security.model.AuthUserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security User Detail Service
 * AuthenticationManager调用Provider，provider调用userDetaisService来根据username获取真实的数据库信息。
 * 而在usernamePasswordAuthenticationFilter中来调用的是AuthenticationManager
 *
 * @author zhangyongji
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * 用户服务
     */
    @Autowired
    private IMchUserFacade mchUserFacade;

    @Autowired
    private WebServiceHelper webServiceHelper;

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        QueryMchUserRequest request = new QueryMchUserRequest();
        webServiceHelper.requestWrapper(request);
        request.setLoginId(loginName);
        QueryMchUserResponse response = mchUserFacade.queryMchUserInfo(request);
        LOGGER.info("调用INS用户查询响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            User user = new User();
            user.setLoginId(response.getLoginId());
            user.setPassword(response.getLoginPwd());
            user.setEnabled(true);
            return AuthUserFactory.create(user);
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
        }
    }
}
