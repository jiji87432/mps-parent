package com.chanpay.ppd.mps.mobile.security;

import com.chanpay.ppd.mps.api.entity.TripUser;
import com.chanpay.ppd.mps.api.service.ITripUserService;
import com.chanpay.ppd.mps.mobile.security.model.AuthUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Security User Detail Service
 *
 * @author zhangxd
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户服务
     */
    @Autowired
    private ITripUserService tripUserService;

    @Override
    public UserDetails loadUserByUsername(String loginName) {
        TripUser user = tripUserService.getByMobile(loginName);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
        } else {
            return AuthUserFactory.create(user);
        }
    }
}
