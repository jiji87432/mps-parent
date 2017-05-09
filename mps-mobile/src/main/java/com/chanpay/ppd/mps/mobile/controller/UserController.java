package com.chanpay.ppd.mps.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.chanpay.ppd.ins.api.mch.facade.IMchUserFacade;
import com.chanpay.ppd.ins.api.mch.facade.dto.*;
import com.chanpay.ppd.ins.api.security.facade.ILoginPwdFacade;
import com.chanpay.ppd.ins.api.security.facade.dto.SetLoginPwdRequest;
import com.chanpay.ppd.ins.api.security.facade.dto.SetLoginPwdResponse;
import com.chanpay.ppd.ins.api.security.facade.dto.UpdateLoginPwdRequest;
import com.chanpay.ppd.ins.api.security.facade.dto.UpdateLoginPwdResponse;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.base.exception.ResetPwdException;
import com.chanpay.ppd.mps.mobile.base.exception.UpdatePwdException;
import com.chanpay.ppd.mps.mobile.base.exception.UpdateUserInfoException;
import com.chanpay.ppd.mps.mobile.base.exception.UserBindException;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.ResetUserLoginPwdRequest;
import com.chanpay.ppd.mps.mobile.entity.UpdateUserInfoRequest;
import com.chanpay.ppd.mps.mobile.entity.UpdateUserLoginPwdRequest;
import com.chanpay.ppd.mps.mobile.entity.UserBindRequest;
import com.chanpay.ppd.mps.mobile.security.model.AuthUser;
import com.chanpay.ppd.mps.web.util.WebUtils;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * The type User controller.
 *
 * @author zhangyogji
 */
@Validated
@RestController
@RequestMapping("/{version}/user")
@Api(tags = "用户管理")
public class UserController extends BaseController {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IMchUserFacade mchUserFacade;

    @Autowired
    private ILoginPwdFacade loginPwdFacade;

    @Autowired
    private WebServiceHelper webServiceHelper;

    /**
     * Registry user map.
     *
     * @param version the version
     * @return the map
     */
    @PostMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "用户绑定")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> registryUser(@RequestBody @Valid UserBindRequest request, BindingResult bindingResult,
                                            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) throws UserBindException {
        AddMchUserRequest addMchUserRequest = new AddMchUserRequest();
        webServiceHelper.requestWrapper(request);
        addMchUserRequest.setHilvId(request.getLoginId());
        addMchUserRequest.setIdType(request.getIdType());
        addMchUserRequest.setLoginId(request.getSubLoginId());
        addMchUserRequest.setMchId(request.getMerId());
        // 注册
        AddMchUserResponse response = mchUserFacade.addMchUser(addMchUserRequest);
        LOGGER.info("调用INS用户绑定响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            Map<String, Object> message = new HashMap<>();
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            return message;
        } else {
            throw new UserBindException(String.format("父用户 '%s' 绑定子用户 '%s' 失败", request.getLoginId(), request.getSubLoginId()));
        }
    }

    /**
     * Gets user.
     *
     * @param version the version
     * @return the user
     */
    @GetMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getUser(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) {
        AuthUser user = WebUtils.getCurrentUser();
        QueryMchUserRequest queryMchUserRequest = new QueryMchUserRequest();
        webServiceHelper.requestWrapper(queryMchUserRequest);
        queryMchUserRequest.setLoginId(user.getLoginId());
        mchUserFacade.queryMchUserInfo(queryMchUserRequest);
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        message.put(BaseResponeMessage.RESP_DATA, "");
        return message;
    }

    /**
     * Update user map.
     * @param version
     * @param request
     * @return
     */
    @PutMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateUser(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version, UpdateUserInfoRequest request, BindingResult bindingResult
    ) throws UpdateUserInfoException {
        UpdateNickNameRequest updateNickNameRequest = new UpdateNickNameRequest();
        webServiceHelper.requestWrapper(request);
        updateNickNameRequest.setLoginId(request.getLoginId());
        updateNickNameRequest.setNickName(request.getNickName());
        UpdateNickNameResponse response = mchUserFacade.updateNickName(updateNickNameRequest);
        LOGGER.info("调用INS更新用户信息响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            Map<String, Object> message = new HashMap<>();
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            return message;
        } else {
            throw new UpdateUserInfoException(String.format("用户 '%s' 信息更新失败", request.getLoginId()));
        }
    }

    /**
     * @param version
     * @param request
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/password", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "重置登录密码")
    public Map<String, Object> resetPassword(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
            ResetUserLoginPwdRequest request, BindingResult bindingResult) throws ResetPwdException {
        SetLoginPwdRequest setLoginPwdRequest = new SetLoginPwdRequest();
        webServiceHelper.requestWrapper(request);
        setLoginPwdRequest.setLoginId(request.getSubLoginId());
        setLoginPwdRequest.setHilvId(request.getLoginId());
        setLoginPwdRequest.setMchId(request.getMerId());
        setLoginPwdRequest.setLoginPwd(request.getPassword());
        SetLoginPwdResponse response = loginPwdFacade.setLoginPwd(setLoginPwdRequest);
        LOGGER.info("调用INS重置登录密码响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            Map<String, Object> message = new HashMap<>();
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            return message;
        } else {
            throw new ResetPwdException(String.format("用户 '%s' 重置登录密码失败", request.getLoginId()));
        }
    }

    /**
     *
     * @param version
     * @param request
     * @param bindingResult
     * @return
     * @throws UpdatePwdException
     */
    @PutMapping(value = "/password", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改登录密码")
    public Map<String, Object> forgetPassword(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
            UpdateUserLoginPwdRequest request, BindingResult bindingResult) throws UpdatePwdException {
        UpdateLoginPwdRequest updateLoginPwdRequest = new UpdateLoginPwdRequest();
        webServiceHelper.requestWrapper(request);
        updateLoginPwdRequest.setLoginId(request.getLoginId());
        updateLoginPwdRequest.setLoginPwd(request.getOldPwd());
        updateLoginPwdRequest.setNewLoginPwd(request.getNewPwd());
        UpdateLoginPwdResponse response = loginPwdFacade.updateLoginPwd(updateLoginPwdRequest);
        LOGGER.info("调用INS修改登录密码响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            Map<String, Object> message = new HashMap<>();
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            return message;
        } else {
            throw new UpdatePwdException(String.format("用户 '%s' 修改登录密码失败", request.getLoginId()));
        }
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UserBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(UserBindException ex) {
        return makeErrorMessage(ReturnCode.USER_BIND_EXIST, "User bind fail", ex.getMessage());
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UpdateUserInfoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(UpdateUserInfoException ex) {
        return makeErrorMessage(ReturnCode.UPDATE_USERINFO_FAIL, "UserInfo update fail", ex.getMessage());
    }
    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(ResetPwdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(ResetPwdException ex) {
        return makeErrorMessage(ReturnCode.RESET_PWD_FAIL, "User reset password fail", ex.getMessage());
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UpdatePwdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(UpdatePwdException ex) {
        return makeErrorMessage(ReturnCode.UPDATE_PWD_FAIL, "User update password fail", ex.getMessage());
    }
}
