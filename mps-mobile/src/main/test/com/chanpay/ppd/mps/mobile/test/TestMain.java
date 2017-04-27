package com.chanpay.ppd.mps.mobile.test;

import com.netfinworks.common.domain.OperationEnvironment;
import com.netfinworks.common.util.money.Money;
import com.netfinworks.tradeservice.facade.api.TradeProcessFacade;
import com.netfinworks.tradeservice.facade.enums.TradeType;
import com.netfinworks.tradeservice.facade.model.AcquiringTradeItemDetail;
import com.netfinworks.tradeservice.facade.model.PaymentInfo;
import com.netfinworks.tradeservice.facade.model.TradeItemDetail;
import com.netfinworks.tradeservice.facade.model.paymethod.OnlineBankPayMethod;
import com.netfinworks.tradeservice.facade.request.TradeRequest;
import com.netfinworks.tradeservice.facade.response.PaymentResponse;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.util.*;


public class TestMain {

	public static void main(String[] args) {
		    JaxWsProxyFactoryBean soapFactoryBean = new JaxWsProxyFactoryBean();
	        soapFactoryBean.setAddress("http://yinternal.chanpay.com/tss/tradeProcessFacade?wsdl");
	        soapFactoryBean.setServiceClass(TradeProcessFacade.class);
	        Object o = soapFactoryBean.create();
	        TradeProcessFacade service=(TradeProcessFacade) o;
	        Money dMoney=new Money("0.01");
	        Random random = new Random();

	    	UUID uuid1=UUID.randomUUID();
			String uuid=String.valueOf(UUID.randomUUID());

	       // String uuid=String.valueOf(randomInt);

	        /////封装request参数
	        TradeRequest tradeRequest=new TradeRequest();
	      //  tradeRequest.setTradeItemDetailList(tradeItemDetailList);
	       // tradeRequest.setTradeItemDetailList(tradeItemDetailList);
	        List tradeItemList=new ArrayList();
	        TradeItemDetail treadeItemDetail= new AcquiringTradeItemDetail();
	        treadeItemDetail.setBizNo(uuid);
	        treadeItemDetail.setBizProductCode("20201"); //暂时定义
	        treadeItemDetail.setPartnerId("200000400059"); //测试商户号
	        treadeItemDetail.setSellerAccountNo("200100100120000040005900001"); //虚户号
	        treadeItemDetail.setSellerId("200000400059"); //商户号
	        treadeItemDetail.setTradeAmount(dMoney);// 付款金额
	        treadeItemDetail.setTradeSourceVoucherNo(uuid); //uuid
	        treadeItemDetail.setTradeType(TradeType.INSTANT_ACQUIRING);
	        treadeItemDetail.setTradeVoucherNo(String.valueOf(UUID.randomUUID()).replaceAll("-", ""));
	        tradeItemList.add(treadeItemDetail);
	        tradeRequest.setTradeItemDetailList(tradeItemList);
	        //////////////////////////////////////////////////////
	        tradeRequest.setBuyerId("anonymous");
	        tradeRequest.setGmtSubmit(new Date());
	        tradeRequest.setAccessChannel("WEB"); //web端发起 WEB wap端发起 WAP

	        PaymentInfo paymentInfo=new PaymentInfo();
	        paymentInfo.setPaymentSourceVoucherNo(String.valueOf(UUID.randomUUID()).replaceAll("-", ""));
	        paymentInfo.setPaymentVoucherNo(String.valueOf(UUID.randomUUID()).replaceAll("-", ""));
	        OnlineBankPayMethod payMethod=new OnlineBankPayMethod();
	        payMethod.setAmount(dMoney);
	        payMethod.setPayChannel("23"); //payChannel  支付渠道   23(网银借记卡) 28（微信） 29(支付宝)
	        payMethod.setPayMode("ONLINE_BANK");
	        payMethod.setBankCode("TESTBANK"); //WXPAY 微信 	ALIPAY 支付宝 TESTBANK 网银
	        payMethod.setCompanyOrPersonal("C"); //	B 对公 C对私
	        payMethod.setDbcr("DC"); //DC 借记卡 GC综合 CC贷记卡

	        List payMethodList=new ArrayList();
	        payMethodList.add(payMethod);
	        paymentInfo.setPayMethodList(payMethodList);

	        /////
	        tradeRequest.setPaymentInfo(paymentInfo);

	        ///封装operation参数
	        OperationEnvironment oe=new OperationEnvironment();
	        oe.setClientId("pos");
	        System.out.println(uuid);
	        PaymentResponse response= service.createAndPay(tradeRequest, oe);
	        System.err.println(response);
	}
}
