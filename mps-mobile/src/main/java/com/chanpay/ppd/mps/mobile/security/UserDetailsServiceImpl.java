package com.chanpay.ppd.mps.mobile.security;

import com.chanpay.ppd.mps.mobile.entity.user.User;
import com.chanpay.ppd.mps.mobile.security.model.AuthUserFactory;
import com.chanpay.ppd.mps.mobile.service.InsRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Security User Detail Service
 *
 * @author zhangyongji
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户服务
     */
    @Autowired
    private InsRpcService insRpcService;

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        //insRpcService.loginAuth();
        User user = new User(true);
        return AuthUserFactory.create(user);
    }
}
