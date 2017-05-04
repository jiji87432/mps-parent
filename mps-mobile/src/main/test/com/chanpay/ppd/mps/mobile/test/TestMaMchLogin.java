package com.chanpay.ppd.mps.mobile.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.ma.service.facade.IMemberFacade;
import com.netfinworks.ma.service.request.CompanyMemberQueryRequest;
import com.netfinworks.ma.service.response.CompanyMemberResponse;

public class TestMaMchLogin {

	public static void main(String[] args) {
        JaxWsProxyFactoryBean soapFactoryBean = new JaxWsProxyFactoryBean();
		soapFactoryBean.setAddress("http://yinternal.chanpay.com/ma-web/MemberFacade?wsdl");
		soapFactoryBean.setServiceClass(IMemberFacade.class);
		Object o = soapFactoryBean.create();
		IMemberFacade service = (IMemberFacade) o;
		OperationEnvironment oe = new OperationEnvironment();
		oe.setClientId("INS");

		CompanyMemberQueryRequest request = new CompanyMemberQueryRequest();
		request.setMemberId("200000400059111");
		CompanyMemberResponse response = service.queryCompanyMember(oe, request);
		System.out.println(response);
		System.out.println(response.getCompanyMemberInfo().getMemberName());
	}
}