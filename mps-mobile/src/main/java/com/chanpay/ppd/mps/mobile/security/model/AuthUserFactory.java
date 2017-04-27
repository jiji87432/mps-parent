package com.chanpay.ppd.mps.mobile.security.model;


import com.chanpay.ppd.mps.api.entity.TripUser;

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
    public static AuthUser create(TripUser user) {
        return new AuthUser(
                user.getId(),
                user.getMobile(),
                user.getPassword(),
                user.getEnabled()
        );
    }
}
