package com.lab.hosaily.core.client.webservice;

import com.lab.hosaily.core.client.service.PaymentService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/1.0/payment")
public class PaymentRestController{

    private static Logger logger = LoggerFactory.getLogger(PaymentRestController.class);

    @Autowired
    private PaymentService paymentService;

    /**
     * 根据购买记录统计微信支付已成功付款金额
     */
    @RequestMapping(value = "/price/{purchaseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Double> priceWechatMerchantPayByPurchaseId(@PathVariable("purchaseId") String purchaseId){
        try{
            double price = paymentService.priceWechatMerchantPayByPurchaseId(purchaseId);

            return new Response<Double>("查询成功", price);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
