package com.chanpay.ppd.mps.mobile.controller;

import com.chanpay.ppd.mos.api.dto.AppPayOrder;
import com.chanpay.ppd.mos.api.payment.dto.PaymentOrderRequest;
import com.chanpay.ppd.mos.api.payment.dto.PaymentOrderResponse;
import com.chanpay.ppd.mos.api.payment.dto.QueryAppPayOrderRequest;
import com.chanpay.ppd.mos.api.payment.dto.QueryAppPayOrderResponse;
import com.chanpay.ppd.mos.api.payment.facade.IPayOrderFacade;
import com.chanpay.ppd.mos.api.trade.dto.PreOrderRequest;
import com.chanpay.ppd.mos.api.trade.dto.PreOrderResponse;
import com.chanpay.ppd.mos.api.trade.facade.ITradeOrderFacade;
import com.chanpay.ppd.mps.mobile.base.BaseResponeMessage;
import com.chanpay.ppd.mps.mobile.base.constant.ParamConstants;
import com.chanpay.ppd.mps.mobile.base.constant.ReturnCode;
import com.chanpay.ppd.mps.mobile.base.exception.MerchantBindException;
import com.chanpay.ppd.mps.mobile.base.exception.OrderCreateFailException;
import com.chanpay.ppd.mps.mobile.base.exception.OrderPayMentFailException;
import com.chanpay.ppd.mps.mobile.common.controller.BaseController;
import com.chanpay.ppd.mps.mobile.common.helper.WebServiceHelper;
import com.chanpay.ppd.mps.mobile.entity.*;
import com.chanpay.ppd.mps.mobile.service.ChanPayRpcService;
import com.chanpay.ppd.mps.mobile.service.InsRpcService;
import com.netfinworks.tradeservice.facade.model.paymethod.OnlineBankPayMethodResult;
import com.netfinworks.tradeservice.facade.model.paymethod.PayMethodResult;
import com.netfinworks.tradeservice.facade.response.PaymentResponse;
import io.swagger.annotations.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @Autowired
    private ITradeOrderFacade tradeOrderFacade;

    @Autowired
    private IPayOrderFacade payOrderFacade;

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
    @PostMapping(value = "/info", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询待支付订单信息")
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
        webServiceHelper.requestWrapper(request);
        queryAppOrderRequest.setPartnerId(request.getPartnerId());
        queryAppOrderRequest.setSellerId(request.getMerId());
        queryAppOrderRequest.setPayOrderNo(request.getPayOrderNo());
        queryAppOrderRequest.setAmount(request.getTradeAmount());
        queryAppOrderRequest.setPayOrderTimeBegin(request.getOrderStartDate());
        queryAppOrderRequest.setPayOrderTimeEnd(request.getOrderEndDate());
        queryAppOrderRequest.setSellerName(request.getBuyerName());
        QueryAppPayOrderResponse response = payOrderFacade.queryAppPayOrder(queryAppOrderRequest);
        List<QueryOrderResponse> list = new ArrayList<QueryOrderResponse>();
        if (null != response && response.getAppPayOrderList().size() > 0) {
            for (AppPayOrder appPayOrder : response.getAppPayOrderList()) {
                QueryOrderResponse queryOrderResponse = new QueryOrderResponse();
                queryOrderResponse.setPayOrderNo(appPayOrder.getPayOrderNo());
                queryOrderResponse.setAmount(appPayOrder.getAmount());
                queryOrderResponse.setOrderSource(appPayOrder.getOrderSource());
                queryOrderResponse.setTradeOrderNo(appPayOrder.getTradeOrderNo());
                queryOrderResponse.setProductName(appPayOrder.getProductName());
                queryOrderResponse.setBuyerName(appPayOrder.getBuyerName());
                queryOrderResponse.setOrderCrtDate(appPayOrder.getSubOrderTime());
                queryOrderResponse.setThirdOrderNo(appPayOrder.getOutTradeNo());
                queryOrderResponse.setRemark(appPayOrder.getSubTradeMemo());
                list.add(queryOrderResponse);
            }
        }
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        message.put(BaseResponeMessage.RESP_DATA, list);
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
    ) throws OrderPayMentFailException, MerchantBindException {
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

    /**
     * createAndPay order.
     *
     * @param version the version
     * @return the user
     */
    @PutMapping(value = "", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "订单创建")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> createOrder(@RequestBody @Valid CreateOrderRequest request, BindingResult bindingResult,
                                           @ApiParam(required = true, value = "版本", defaultValue = "v1") @PathVariable("version") String version
    ) throws OrderCreateFailException, MerchantBindException {
        insRpcService.validateMerNo(request.getPartnerId(), request.getMerId());
        //首先调用mos创建主订单
        PreOrderRequest preOrderRequest = new PreOrderRequest();
        webServiceHelper.requestWrapper(request);
        preOrderRequest.setPartnerId(request.getPartnerId());
        preOrderRequest.setOutTradeNo(request.getOutTradeNo());
        preOrderRequest.setTradeAmount(request.getTradeAmount());
        preOrderRequest.setProductName(request.getProductName());
        preOrderRequest.setBuyerId(request.getMerId());
        preOrderRequest.setBuyerName(request.getBuyerName());
        preOrderRequest.setOrderTime(request.getOrderTime());
        preOrderRequest.setExpiredTime(request.getExpiredTime());
        preOrderRequest.setOrderSource(request.getOrderSource());
        PreOrderResponse preOrderResponse = tradeOrderFacade.preOrder(preOrderRequest);
        PaymentOrderRequest paymentOrderRequest = new PaymentOrderRequest();
        webServiceHelper.requestWrapper(request);
        paymentOrderRequest.setPartnerId(request.getPartnerId());
        paymentOrderRequest.setTradeOrderNo(preOrderResponse.getTradeOrderNo());
        paymentOrderRequest.setTradeAmount(request.getTradeAmount());
        paymentOrderRequest.setPayTool(request.getPayTool());
        paymentOrderRequest.setSellerId(request.getMerId());
        paymentOrderRequest.setSellerName(request.getBuyerName());
        paymentOrderRequest.setImei(request.getImei());
        PaymentOrderResponse paymentOrderResponse = payOrderFacade.paymentOrder(paymentOrderRequest);
        CreateOrderResponse response = new CreateOrderResponse();
        response.setPayOrderNo(paymentOrderResponse.getPayOrderNo());
        response.setTradeOrderNo(paymentOrderResponse.getTradeOrderNo());
        response.setTradeAmount(paymentOrderResponse.getAmount());
        response.setOrderStatus(paymentOrderResponse.getStatus());
        //在创建子订单，即订单支付流水
        Map<String, Object> message = new HashMap<>();
        message.put(BaseResponeMessage.RESP_CODE, ReturnCode.SUCCESS);
        message.put(BaseResponeMessage.RESP_CODE_DESC, ReturnCode.SUCCESS_DESC);
        message.put(BaseResponeMessage.RESP_DATA, response);
        return message;
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(OrderPayMentFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(OrderPayMentFailException ex) {
        return makeErrorMessage(ReturnCode.ORDER_PAYMENT_FAIL, "order payment fail", ex.getMessage());
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(MerchantBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(MerchantBindException ex) {
        return makeErrorMessage(ReturnCode.INVALID_MERCHANT_BIND, "invalid merchant bind no exist", ex.getMessage());
    }

    /**
     * Handle sms too much exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(OrderCreateFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleSmsTooMuchException(OrderCreateFailException ex) {
        return makeErrorMessage(ReturnCode.ORDER_CREATE_FAIL, "order create fail", ex.getMessage());
    }
}
