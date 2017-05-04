package com.chanpay.ppd.mps.mobile.controller;

import com.chanpay.ppd.mps.api.exception.UserNotExistException;
import com.chanpay.ppd.mps.api.exception.UserPwdErrorException;
import com.chanpay.ppd.mps.api.exception.base.BusinessException;
import com.chanpay.ppd.mps.api.service.ITripUserService;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.entity.UserLoginRequest;
import com.chanpay.ppd.mps.mobile.security.utils.TokenUtil;
import com.chanpay.ppd.mps.web.security.AuthenticationTokenFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
     * 用户服务
     */
    @Autowired
    private ITripUserService tripUserService;
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

    /**
     * Create authentication token bearer auth token.
     * @param request
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/token")
    @ApiOperation("登录鉴权")
    public Map<String, Object> createAuthenticationToken(@RequestBody UserLoginRequest request) throws BusinessException {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(request.getLoginId(), request.getPassword());
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);
        // Return the token
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", token);
        tokenMap.put("expirationDate", jwtTokenUtil.getExpiration());
        tokenMap.put("tokenType", TokenUtil.TOKEN_TYPE_BEARER);
        return tokenMap;
    }

    /**
     * Refresh and get authentication token bearer auth token.
     *
     * @param request the request
     * @return the bearer auth token
     */
    @GetMapping(value = "/refresh")
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
    @ExceptionHandler(UserNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(UserNotExistException ex) {
        return makeErrorMessage(ReturnCode.USER_EXIST, "User not exist", ex.getMessage());
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UserPwdErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(UserPwdErrorException ex) {
        return makeErrorMessage(ReturnCode.INVALID_GRANT, "User pwd error", ex.getMessage());
    }
}
