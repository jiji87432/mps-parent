package com.chanpay.ppd.mps.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import com.chanpay.ppd.ins.api.config.facade.IMchAuthFacade;
import com.chanpay.ppd.ins.api.config.facade.dto.*;
import com.chanpay.ppd.ins.api.mch.facade.IMchUserFacade;
import com.chanpay.ppd.ins.api.security.facade.ILoginPwdFacade;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.base.exception.MerPermissionException;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.AddMerPermissionsRequest;
import com.chanpay.ppd.mps.mobile.entity.QueryMerPermissionsRequest;
import com.chanpay.ppd.mps.mobile.entity.QueryMerPermissionsResponse;
import com.chanpay.ppd.mps.mobile.entity.UpdateMerPermissionsRequest;
import com.chanpay.ppd.mps.web.base.exception.BusinessException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * The type User controller.
 *
 * @author zhangyogji
 */
@Validated
@RestController
@RequestMapping("/{version}/mer")
@Api(tags = "商户管理")
public class MerController extends BaseController {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MerController.class);

    @Autowired
    private IMchUserFacade mchUserFacade;

    @Autowired
    private IMchAuthFacade mchAuthFacade;

    @Autowired
    private ILoginPwdFacade loginPwdFacade;

    @Autowired
    private WebServiceHelper webServiceHelper;

    /**
     * @param version
     * @param request
     * @param bindingResult
     * @return
     * @throws MerPermissionException
     */
    @PostMapping(value = "/permission", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "权限配置")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> registryUser(@ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
                                            AddMerPermissionsRequest request, BindingResult bindingResult) throws MerPermissionException {
        AddMchAuthRequest addMchAuthRequest = new AddMchAuthRequest();
        webServiceHelper.requestWrapper(request);
        addMchAuthRequest.setMchId(request.getMerId());
        addMchAuthRequest.setMchNm(request.getMerName());
        // 权限配置添加
        AddMchAuthResponse response = mchAuthFacade.addMchAuth(addMchAuthRequest);
        LOGGER.info("调用INS权限配置响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            Map<String, Object> message = new HashMap<>();
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            return message;
        } else {
            throw new MerPermissionException(String.format("商户 '%s' 添加权限异常", request.getMerId()));
        }
    }

    /**
     * @param version
     * @param request
     * @param bindingResult
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询商户权限")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getUser(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
            QueryMerPermissionsRequest request, BindingResult bindingResult) throws BusinessException {
        QueryMchAuthRequest queryMchAuthRequest = new QueryMchAuthRequest();
        webServiceHelper.requestWrapper(request);
        queryMchAuthRequest.setMchId(request.getMerId());
        QueryMchAuthResponse response = mchAuthFacade.queryMchAuth(queryMchAuthRequest);
        LOGGER.info("调用INS权限配置响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            Map<String, Object> message = new HashMap<>();
            QueryMerPermissionsResponse queryMerPermissionsResponse = new QueryMerPermissionsResponse();
            queryMerPermissionsResponse.setAppSrvFee(response.getAppSrvFee());
            queryMerPermissionsResponse.setCashSrvFee(response.getAppSrvFee());
            queryMerPermissionsResponse.setIsOpenApp(response.getIsOpenApp());
            queryMerPermissionsResponse.setIsOpenCash(response.getIsOpenCash());
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            message.put(BaseResponeMessage.RESP_DATA, queryMerPermissionsResponse);
            return message;
        } else {
            throw new BusinessException(String.format("商户 '%s' 查询权限异常", request.getMerId()));
        }
    }

    /**
     * Update user map.
     *
     * @param version
     * @param request
     * @return
     */
    @PutMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新商户配置")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateUser(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
            UpdateMerPermissionsRequest request, BindingResult bindingResult) throws BusinessException {
        UpdateMchAuthRequest updateMchAuthRequest = new UpdateMchAuthRequest();
        webServiceHelper.requestWrapper(request);
        updateMchAuthRequest.setAppSrvFee(request.getAppSrvFee());
        updateMchAuthRequest.setCashSrvFee(request.getCashSrvFee());
        updateMchAuthRequest.setIsOpenApp(request.getIsOpenApp());
        updateMchAuthRequest.setIsOpenCash(request.getIsOpenCash());
        updateMchAuthRequest.setMchId(request.getMerId());
        UpdateMchAuthResponse response = mchAuthFacade.updateMchAuth(updateMchAuthRequest);
        LOGGER.info("调用INS更新商户配置信息响应对象：", null != response ? JSONObject.toJSONString(response) : response);
        if (null != response && response.getRespCode().equals(ParamConstants.InsFacade.SUCCESS)) {
            Map<String, Object> message = new HashMap<>();
            message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
            message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
            return message;
        } else {
            throw new BusinessException(String.format("更新商户 '%s' 配置信息更新失败", request.getMerId()));
        }
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(MerPermissionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(MerPermissionException ex) {
        return makeErrorMessage(ReturnCode.MERPERMISSION_ADD_FAIL, "mer permission add fail", ex.getMessage());
    }
}
