package com.chanpay.ppd.mps.mobile.test;

import com.chanpay.ppd.ins.api.security.facade.IAuthFacade;
import com.chanpay.ppd.ins.api.security.facade.dto.LoginAuthRequest;
import com.chanpay.ppd.ins.api.security.facade.dto.LoginAuthResponse;
import com.chanpay.ppd.mps.common.utils.SHAUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 
 * <b>Description:</b>鏀粯璁㈠崟鍗曞厓娴嬭瘯<br/>
 * <b>CreateTime:</b> 2017骞�4鏈�20鏃� 涓嬪崍2:09:25<br/>
 * 
 * @author dongyinghui
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthFacadeTest {

	@Resource
    private IAuthFacade insAuthFacade;

	@Test
	public void testPreOrder() {
		LoginAuthRequest request = new LoginAuthRequest();
		request.setLoginId("13612345678");
		request.setLoginPwd(SHAUtil.encode("chanpay123456"));
		request.setIdType("01");
		request.setFlowNo(String.valueOf(new Date().getTime()));
		request.setReqSysDate(new Date());
		request.setOperSys("INS");
		request.setImei("13612345678200000400059");
		LoginAuthResponse repsone = insAuthFacade.loginAuth(request);
		System.out.println(repsone);
	}
}