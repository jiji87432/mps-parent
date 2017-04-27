package com.chanpay.ppd.mps.mobile.controller;

import com.chanpay.ppd.mos.api.payment.dto.QueryAppPayOrderRequest;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.PayOrderRequest;
import com.chanpay.ppd.mps.mobile.entity.PayOrderResponse;
import com.chanpay.ppd.mps.mobile.entity.QueryOrderRequest;
import com.chanpay.ppd.mps.mobile.service.ChanPayRpcService;
import com.chanpay.ppd.mps.mobile.service.InsRpcService;
import com.chanpay.ppd.mps.web.base.exception.BusinessException;
import com.netfinworks.tradeservice.facade.model.paymethod.OnlineBankPayMethodResult;
import com.netfinworks.tradeservice.facade.model.paymethod.PayMethodResult;
import com.netfinworks.tradeservice.facade.response.PaymentResponse;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type User controller.
 *
 * @author zhangyogji
 */
@Validated
@RestController
@RequestMapping("/{version}/order")
@Api(tags = "订单管理")
public class OrderController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    /**
     * 用户服务
     */
    //@Autowired
    //private IPayOrderFacade payOrderFacade;

    @Autowired
    private ChanPayRpcService chanPayRpcService;

    @Autowired
    private InsRpcService insRpcService;

    @Autowired
    private WebServiceHelper webServiceHelper;

    /**
     * Gets order.
     *
     * @param version the version
     * @return the user
     */
    @PutMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询订单信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getOrder(@RequestBody @Valid QueryOrderRequest request, BindingResult bindingResult,
                                        @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) {
        QueryAppPayOrderRequest queryAppOrderRequest = new QueryAppPayOrderRequest();
        webServiceHelper.requestWrapper(queryAppOrderRequest);
        //queryAppOrderRequest.setPartnerId(request.getPartnerId());
        //queryAppOrderRequest.setSellerId(request.getMerId());
        //queryAppOrderRequest.setPayOrderNo(request.getPayOrderNo());
        //queryAppOrderRequest.setAmount(request.getTradeAmount());
        //queryAppOrderRequest.setPayOrderTimeBegin(request.getOrderStartDate());
        //queryAppOrderRequest.setPayOrderTimeEnd(request.getOrderEndDate());
        //queryAppOrderRequest.setSellerName(request.getBuyerName());
        //QueryAppPayOrderResponse response = payOrderFacade.queryAppPayOrder(queryAppOrderRequest);
        //List<QueryOrderResponse> list = new ArrayList<QueryOrderResponse>();
        //if (null != response && response.getAppPayOrderList().size() > 0) {
        //    for (AppPayOrder appPayOrder : response.getAppPayOrderList()) {
        //        QueryOrderResponse queryOrderResponse = new QueryOrderResponse();
        //        queryOrderResponse.setPayOrderNo(appPayOrder.getPayOrderNo());
        //        queryOrderResponse.setAmount(appPayOrder.getAmount());
        //        queryOrderResponse.setOrderSource(appPayOrder.getOrderSource());
        //        queryOrderResponse.setTradeOrderNo(appPayOrder.getTradeOrderNo());
        //        queryOrderResponse.setProductName(appPayOrder.getProductName());
        //        queryOrderResponse.setBuyerName(appPayOrder.getBuyerName());
        //        queryOrderResponse.setOrderCrtDate(appPayOrder.getSubOrderTime());
        //        queryOrderResponse.setThirdOrderNo(appPayOrder.getOutTradeNo());
        //        queryOrderResponse.setRemark(appPayOrder.getSubTradeMemo());
        //        list.add(queryOrderResponse);
        //    }
        //}
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        //message.put(BaseResponeMessage.RESP_DATA, list);
        return message;
    }


    /**
     * createAndPay order.
     *
     * @param version the version
     * @return the user
     */
    @PostMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "订单支付")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> createAndPay(@RequestBody @Valid PayOrderRequest request, BindingResult bindingResult,
                                            @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) throws BusinessException {
        insRpcService.validateMerNo(request.getPartnerId(), request.getMerId());
        PaymentResponse response = chanPayRpcService.createAndPay(request);
        PayOrderResponse payOrderResponse = new PayOrderResponse();
        List<PayMethodResult> payResult = response.getPayMethodResultList();
        if (payResult != null && payResult.size() > 0) {
            for (PayMethodResult payRes : payResult) {
                if (payRes instanceof OnlineBankPayMethodResult) {
                    OnlineBankPayMethodResult result = (OnlineBankPayMethodResult) payRes;
                    Map<String, Object> resultExtensionMap = JSONObject.fromObject(result.getResultExtension());
                    if (null != resultExtensionMap && null != resultExtensionMap.get("code_url")) {
                        payOrderResponse.setPayUrl(resultExtensionMap.get("code_url").toString());
                    }
                }
            }

        }
        Map<String, Object> message = new HashMap<>();
        if (response != null) {
            LOGGER.info("error message:" + response.getErrorCode(), response.getResultMessage());
            if (ParamConstants.TradeService.SUCCESS.equals(response.getErrorCode().equals("S0001"))) {
                message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
                message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
                message.put(BaseResponeMessage.RESP_DATA, payOrderResponse);
            }
        }
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        message.put(BaseResponeMessage.RESP_DATA, payOrderResponse);
        return message;
    }

}
