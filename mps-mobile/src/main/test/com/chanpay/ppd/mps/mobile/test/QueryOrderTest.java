package com.chanpay.ppd.mps.mobile.test;

import com.netfinworks.tradeservice.facade.apiext.TradeQueryExtFacade;
import com.netfinworks.tradeservice.facade.request.TradeOrderRequestExt;
import com.netfinworks.tradeservice.facade.response.TradeOrderResponse;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class QueryOrderTest {
	public static void main(String[] args) {
		 JaxWsProxyFactoryBean soapFactoryBean = new JaxWsProxyFactoryBean();
	        soapFactoryBean.setAddress("http://123.103.9.206:8273/tss-site/tradeQueryExtFacade?wsdl");
	        soapFactoryBean.setServiceClass(TradeQueryExtFacade.class);
	        Object o = soapFactoryBean.create();
	        TradeQueryExtFacade service=(TradeQueryExtFacade) o;
	        /////封装request参数
			TradeOrderRequestExt tradeOrderRequest=new TradeOrderRequestExt();
			tradeOrderRequest.setTradeVoucherNo("epos02df91ddb097411cb05");
	        TradeOrderResponse response= service.queryTradeOrderByTradeVoucherNo(tradeOrderRequest);
	        System.err.println(response);
	}
}
