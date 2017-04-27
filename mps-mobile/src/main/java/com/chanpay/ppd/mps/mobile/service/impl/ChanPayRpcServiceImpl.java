package com.chanpay.ppd.mps.mobile.service.impl;

import com.chanpay.ppd.mps.common.utils.SeqUtils;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.base.exception.OrderPayMentFailException;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.PayOrderRequest;
import com.chanpay.ppd.mps.mobile.service.ChanPayRpcService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiji on 2017/4/26.
 */
@Controller
public class ChanPayRpcServiceImpl implements ChanPayRpcService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChanPayRpcServiceImpl.class);

    @Value("${chanpay.tradeWsUrl}")
    private String tradeWsUrl;

    @Autowired
    private WebServiceHelper webServiceHelper;

    @Override
    public PaymentResponse createAndPay(PayOrderRequest payOrderRequest) throws OrderPayMentFailException {
        TradeRequest tradeRequest = new TradeRequest();
        List tradeItemList = new ArrayList();
        TradeItemDetail treadeItemDetail = new AcquiringTradeItemDetail();
        treadeItemDetail.setBizNo(SeqUtils.getUniqueID());
        treadeItemDetail.setBizProductCode(ParamConstants.ProductCode.INSTANT_PAY);
        treadeItemDetail.setPartnerId(payOrderRequest.getPartnerId()); //商户号
        treadeItemDetail.setSellerAccountNo("200100100120000040005900001"); //虚户号
        treadeItemDetail.setSellerId(payOrderRequest.getMerId()); //商户号
        treadeItemDetail.setTradeAmount(new Money(payOrderRequest.getTradeAmount()));// 付款金额
        treadeItemDetail.setTradeType(TradeType.INSTANT_ACQUIRING);
        treadeItemDetail.setTradeVoucherNo(payOrderRequest.getPayOrderNo());
        treadeItemDetail.setTradeSourceVoucherNo(SeqUtils.getUniqueID());
        tradeItemList.add(treadeItemDetail);
        tradeRequest.setTradeItemDetailList(tradeItemList);
        tradeRequest.setBuyerId("anonymous");
        tradeRequest.setGmtSubmit(new Date());
        tradeRequest.setAccessChannel("WAP"); //web端发起 WEB wap端发起 WAP
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentVoucherNo(payOrderRequest.getPayOrderNo());
        paymentInfo.setPaymentSourceVoucherNo(SeqUtils.getUniqueID());
        OnlineBankPayMethod payMethod = new OnlineBankPayMethod();
        payMethod.setAmount(new Money(payOrderRequest.getTradeAmount()));
        //payChannel  支付渠道   23(网银借记卡) 28（微信） 29(支付宝)
        //bankCode WXPAY 微信 	ALIPAY 支付宝
        if (payOrderRequest.getPayTool().startsWith("W")) {
            payMethod.setPayChannel(ParamConstants.PayChannel.WXPAY);
            payMethod.setBankCode(ParamConstants.BankCode.WXPAY);
        } else if (payOrderRequest.getPayTool().startsWith("A")) {
            payMethod.setPayChannel(ParamConstants.PayChannel.ALIPAY);
            payMethod.setBankCode(ParamConstants.BankCode.ALIPAY);
        }
        payMethod.setPayMode("ONLINE_BANK");
        payMethod.setCompanyOrPersonal("C"); //	B 对公 C对私
        payMethod.setDbcr("GC"); //DC 借记卡 GC综合 CC贷记卡
        List payMethodList = new ArrayList();
        payMethodList.add(payMethod);
        paymentInfo.setPayMethodList(payMethodList);
        tradeRequest.setPaymentInfo(paymentInfo);
        ///封装operation参数
        OperationEnvironment opEnv = webServiceHelper.buildOpEnv();
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setServiceClass(TradeProcessFacade.class);
        LOGGER.debug("jaxWsURL:" + tradeWsUrl);
        jaxWsProxyFactoryBean.setAddress(tradeWsUrl);
        TradeProcessFacade facade = (TradeProcessFacade)jaxWsProxyFactoryBean.create();
        PaymentResponse response = facade.createAndPay(tradeRequest, opEnv);
        if (response != null) {
            LOGGER.info("response message:" + response.getErrorCode(), response.getResultMessage());
            if (!ParamConstants.TradeService.SUCCESS.equals(response.getErrorCode().equals("S0001"))) {
                throw new OrderPayMentFailException(String.format("订单 '%s' 支付失败", response.getPaymentVoucherNo()));
            }
        }
        return response;
    }
}
