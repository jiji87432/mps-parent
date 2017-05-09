package com.chanpay.ppd.mps.mobile.common.config;

import com.chanpay.ppd.mps.web.config.AbstractWebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * spring-security配置
 *
 * @author zhnagyongji
 */
@Configuration
public class WebSecurityConfig extends AbstractWebSecurityConfig {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略权限校验的访问路径
        web
                .ignoring()
                .antMatchers(
                        "/hello",
                        "/favicon.ico",
                        "/swagger**/**",
                        "/*/api-docs",
                        "/webjars/**",
                        "/*/sms/captcha",
                        "/*/user/password",
                        "/*/currency/**"
                );
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth/**").permitAll();// 对于获取token的rest api要允许匿名访问
        super.configure(security);
    }
}
