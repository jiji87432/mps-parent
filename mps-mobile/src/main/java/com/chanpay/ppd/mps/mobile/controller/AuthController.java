package com.chanpay.ppd.mps.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.chanpay.ppd.ins.api.security.facade.IAuthFacade;
import com.chanpay.ppd.ins.api.security.facade.dto.LoginAuthRequest;
import com.chanpay.ppd.ins.api.security.facade.dto.LoginAuthResponse;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.base.exception.AuthException;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.UserLoginRequest;
import com.chanpay.ppd.mps.mobile.security.utils.TokenUtil;
import com.chanpay.ppd.mps.web.security.AuthenticationTokenFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Authentication controller.
 *
 * @author zhangyongji
 */
@RestController
@RequestMapping("/auth")
@Api(tags = "鉴权管理")
public class AuthController extends BaseController {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IAuthFacade authFacade;

    /**
     * 权限管理
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Token工具
     */
    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private WebServiceHelper webServiceHelper;

    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create authentication token bearer auth token.
     * @param request
     * @return
     * @throws AuthException
     */
    @PostMapping(value = "/token")
    @ApiOperation("登录鉴权")
    public Map<String, Object> createAuthenticationToken(@RequestBody UserLoginRequest request, BindingResult bindingResult) throws AuthException {
        LoginAuthRequest loginAuthRequest = new LoginAuthRequest();
        webServiceHelper.requestWrapper(request);
        loginAuthRequest.setLoginId(request.getLoginId());
        loginAuthRequest.setIdType(request.getIdType());
        loginAuthRequest.setLoginPwd(passwordEncoder.encode(request.getPassword()));
        LoginAuthResponse response = authFacade.loginAuth(loginAuthRequest);
        LOGGER.info("调用登录鉴权响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword());
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final String token = jwtTokenUtil.generateToken(userDetails);
            // Return the token
            Map<String, Object> message = new HashMap<>();
            message.put("accessToken", token);
            message.put("expirationDate", jwtTokenUtil.getExpiration());
            message.put("tokenType", TokenUtil.TOKEN_TYPE_BEARER);
            message.put("tokenType", TokenUtil.TOKEN_TYPE_BEARER);
            message.put("partnerId", response.getPartnerId());
            message.put("merId", response.getSellerId());
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            return message;
        } else {
            throw new AuthException(String.format("用户 '%s' 登录鉴权失败", request.getLoginId()));
        }
    }

    /**
     * Refresh and get authentication token bearer auth token.
     *
     * @param request the request
     * @return the bearer auth token
     */
    @GetMapping(value = "/token")
    @ApiOperation("刷新token")
    public Map<String, Object> refreshAndGetAuthenticationToken(HttpServletRequest request) {

        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String refreshedToken = jwtTokenUtil.generateToken(userDetails);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", refreshedToken);
        tokenMap.put("expirationDate", jwtTokenUtil.getExpiration());
        tokenMap.put("tokenType", TokenUtil.TOKEN_TYPE_BEARER);
        return tokenMap;
    }

    /**
     * Delete authentication token response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @DeleteMapping(value = "/token")
    @ApiOperation("删除token")
    public ResponseEntity deleteAuthenticationToken(HttpServletRequest request) {

        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        jwtTokenUtil.removeToken(token);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(AuthException ex) {
        return makeErrorMessage(ReturnCode.AUTH_FAIL, "auth fail", ex.getMessage());
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(UsernameNotFoundException ex) {
        return makeErrorMessage(ReturnCode.USER_NOT_EXIST, "user not exist", ex.getMessage());
    }

}
