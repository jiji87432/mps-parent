package com.chanpay.ppd.mps.mobile.security.model;


import com.chanpay.ppd.mps.mobile.entity.user.User;

/**
 * The type Auth user factory.
 *
 * @author zhangyongji
 */
public final class AuthUserFactory {

    private AuthUserFactory() {
    }

    /**
     * Create auth user.
     *
     * @param user the user
     * @return the auth user
     */
    public static AuthUser create(User user) {
        return new AuthUser(
                user.getLoginId(),
                user.getPassword(),
                user.getEnabled()
        );
    }
}
