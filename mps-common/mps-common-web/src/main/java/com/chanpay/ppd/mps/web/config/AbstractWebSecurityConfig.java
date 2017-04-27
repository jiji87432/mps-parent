package com.chanpay.ppd.mps.web.config;

import com.chanpay.ppd.mps.web.security.AuthenticationTokenFilter;
import com.chanpay.ppd.mps.web.security.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring-security配置
 *
 * @author zhangyongji
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class AbstractWebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Password encoder password encoder.
     * 装载BCrypt密码编码器,8位加盐
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)// 设置UserDetailsService
                .passwordEncoder(this.passwordEncoder())// 使用BCrypt进行密码的hash
        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Authentication token filter bean authentication token filter.
     *
     * @return the authentication token filter
     */
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() {
        return new AuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable()// 由于使用的是JWT，我们这里不需要csrf
                .exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint()).and()//异常捕获
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()// 基于token，所以不需要session
                .authorizeRequests().anyRequest().authenticated();// 所有请求被验证

        // 禁用缓存
        security.headers().cacheControl();
        // Custom JWT based security filter
        security.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
}
