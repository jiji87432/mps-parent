package com.chanpay.ppd.mps.mobile.controller;

import com.chanpay.ppd.ins.api.mch.facade.IMchUserFacade;
import com.chanpay.ppd.ins.api.mch.facade.dto.AddMchUserRequest;
import com.chanpay.ppd.ins.api.mch.facade.dto.UpdateNickNameRequest;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.base.exception.ResetPwdException;
import com.chanpay.ppd.mps.mobile.base.exception.UpdatePwdException;
import com.chanpay.ppd.mps.mobile.base.exception.UserBindException;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.ResetUserLoginPwdRequest;
import com.chanpay.ppd.mps.mobile.entity.UpdateUserInfoRequest;
import com.chanpay.ppd.mps.mobile.entity.UserBindRequest;
import com.chanpay.ppd.mps.mobile.security.model.AuthUser;
import com.chanpay.ppd.mps.web.util.WebUtils;
import io.swagger.annotations.*;
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

    private IMchUserFacade mchUserFacade;

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
        mchUserFacade.addMchUser(addMchUserRequest);
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        return message;
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
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateUser(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version, UpdateUserInfoRequest request, BindingResult bindingResult
    ) {
        UpdateNickNameRequest updateNickNameRequest = new UpdateNickNameRequest();
        webServiceHelper.requestWrapper(request);
        updateNickNameRequest.setLoginId(request.getLoginId());
        updateNickNameRequest.setNickName(request.getNickName());
        mchUserFacade.updateNickName(updateNickNameRequest);
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        return message;
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

        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        return message;
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
            UpdateUserInfoRequest request, BindingResult bindingResult) throws UpdatePwdException {

        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        return message;
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
        return makeErrorMessage(ReturnCode.USER_BIND_EXIST, "User bind exist", ex.getMessage());
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
