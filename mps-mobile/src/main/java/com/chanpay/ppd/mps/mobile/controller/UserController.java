package com.chanpay.ppd.mps.mobile.controller;

import com.chanpay.ppd.ins.api.mch.facade.IMchUserFacade;
import com.chanpay.ppd.ins.api.mch.facade.dto.AddMchUserRequest;
import com.chanpay.ppd.ins.api.mch.facade.dto.UpdateNickNameRequest;
import com.chanpay.ppd.mps.api.entity.TripUser;
import com.chanpay.ppd.mps.api.exception.InvalidCaptchaException;
import com.chanpay.ppd.mps.api.exception.UserExistException;
import com.chanpay.ppd.mps.api.exception.base.BusinessException;
import com.chanpay.ppd.mps.api.service.ICaptchaService;
import com.chanpay.ppd.mps.api.service.ITripUserService;
import com.chanpay.ppd.mps.common.upload.util.FileIndex;
import com.chanpay.ppd.mps.common.upload.util.FileManager;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.UpdateUserInfoRequest;
import com.chanpay.ppd.mps.mobile.entity.UserBindRequest;
import com.chanpay.ppd.mps.mobile.security.model.AuthUser;
import com.chanpay.ppd.mps.web.util.WebUtils;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Collections;
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
     * 用户服务
     */
    @Autowired
    private ITripUserService tripUserService;

    private IMchUserFacade mchUserFacade;
    @Autowired
    private WebServiceHelper webServiceHelper;
    /**
     * 验证码服务
     */
    @Autowired
    private ICaptchaService captchaService;
    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 文件管理
     */
    @Autowired
    private FileManager fileManager;

    /**
     * Registry user map.
     *
     * @param version the version
     * @return the map
     * @throws InvalidCaptchaException the invalid captcha exception
     */
    @PostMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "用户注册")
    public Map<String, Object> registryUser(@RequestBody @Valid UserBindRequest request, BindingResult bindingResult,
                                            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) throws UserExistException {
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

        TripUser tripUser = tripUserService.get(user.getId());

        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        message.put(BaseResponeMessage.RESP_DATA, tripUser);
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
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version, UpdateUserInfoRequest request
    ) {
        UpdateNickNameRequest updateNickNameRequest = new UpdateNickNameRequest();
        webServiceHelper.requestWrapper(request);
        updateNickNameRequest.setLoginId(request.getLoginId());
        updateNickNameRequest.setIdType(request.getIdType());
        updateNickNameRequest.setNickName(request.getNickName());
        mchUserFacade.updateNickName(updateNickNameRequest);
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        return message;
    }

    /**
     * Forget password map.
     *
     * @param version  the version
     * @param password the password
     * @param mobile   the mobile
     * @param captcha  the captcha
     * @return the map
     * @throws InvalidCaptchaException the invalid captcha exception
     */
    @PutMapping(value = "/password", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "忘记密码")
    public Map<String, Object> forgetPassword(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
            @Length(min = 6, max = 20, message = "密码长度为6到20")
            @ApiParam(required = true, value = "密码") @RequestParam("password") String password,
            @Pattern(regexp = "1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}", message = "手机号码格式错误")
            @ApiParam(required = true, value = "手机号") @RequestParam("mobile") String mobile,
            @Pattern(regexp = "\\d{6}", message = "验证码为6位数字")
            @ApiParam(required = true, value = "验证码") @RequestParam("captcha") String captcha
    ) throws BusinessException {
        //校验验证码
        captchaService.validCaptcha(mobile, captcha);

        // 忘记密码
        tripUserService.updatePasswordByMobile(mobile, passwordEncoder.encode(password));

        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        return message;
    }

    /**
     * Upload photo map.
     *
     * @param version the version
     * @param photo   the photo
     * @return the map
     */
    @PostMapping(value = "/photo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "上传头像")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> uploadPhoto(
            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version,
            @ApiParam(required = true, value = "头像") @RequestParam(value = "photo") MultipartFile photo
    ) {
        String path = "";
        if (photo != null && photo.getSize() > 0) {
            AuthUser user = WebUtils.getCurrentUser();
            FileIndex ufi = WebUtils.buildFileIndex(photo, TripUser.IMAGE_FOLDER);
            ufi = fileManager.save(ufi);
            path = ufi.getPath();
            tripUserService.updatePhotoByUserId(user.getId(), path);
        }

        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        message.put(BaseResponeMessage.RESP_DATA, Collections.singletonMap("path", fileManager.getFileUrlByRealPath(path)));
        return message;
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(UserExistException ex) {
        return makeErrorMessage(ReturnCode.USER_EXIST, "User exist", ex.getMessage());
    }
}
