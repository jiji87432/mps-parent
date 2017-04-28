package com.chanpay.ppd.mps.mobile.common.helper;

import com.chanpay.ppd.mps.mobile.base.BaseRequestMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.common.lang.StringUtil;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhangyongji
 */
@Component
public class WebServiceHelper {

    private static OperationEnvironment opEnv = new OperationEnvironment();

    public void requestWrapper(BaseRequestMessage requestDto) {
        requestDto.setOperSys(requestDto.getOperSys());
        requestDto.setReqSysDate(requestDto.getReqSysDate());
        requestDto.setFlowNo(requestDto.getFlowNo());
    }

    public static OperationEnvironment buildOpEnv() {
        opEnv.setClientId(ParamConstants.OPER_SYS_CODE);
        opEnv.setClientIp(getHostIP());
        opEnv.setClientMac(getHostIP());
        return opEnv;
    }

    /**
     * 获得本机ip
     * @return
     */
    private static String getHostIP() {
        String machineIp = null;
        try {
            machineIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return StringUtil.defaultIfBlank(machineIp);
    }
}
