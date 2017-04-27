package com.chanpay.ppd.mps.mobile.security.utils;

import com.chanpay.ppd.mps.mobile.security.model.AuthUser;
import com.chanpay.ppd.mps.web.security.AbstractTokenUtil;
import com.google.gson.Gson;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The type Token util.
 *
 * @author zhangyongji
 */
@Component
@ConfigurationProperties("security.jwt")
public class TokenUtil extends AbstractTokenUtil {

    @Override
    public UserDetails getUserDetails(String token) {
        String userDetailsString = getUserDetailsString(token);
        if (userDetailsString != null) {
            return new Gson().fromJson(userDetailsString, AuthUser.class);
        }
        return null;
    }

}