package com.lab.hosaily.core.client.webservice;

import com.lab.hosaily.core.application.utils.XMLUtils;
import com.lab.hosaily.core.client.service.WechatMerchantPayService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/xcxPay")
public class XcxPayRestController {

    private static Logger logger = LoggerFactory.getLogger(XcxPayRestController.class);

    @Autowired
    private WechatMerchantPayService wechatMerchantPayService;

    @RequestMapping(value = "/prepay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, String>> prepay(String projectId, String accountId, Double totalFee, String code){
        try{
            Map<String, String> params = wechatMerchantPayService.xcxPrepay(projectId, accountId, totalFee, code);

            return new Response<Map<String, String>>("预支付成功", params);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 支付回调
     */
    @RequestMapping(value = "/callback", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_XML_VALUE)
    public String callback(HttpServletRequest request, HttpServletResponse response){
        try{
            String xml = XMLUtils.read(request.getInputStream());

            return wechatMerchantPayService.xcxCallback(xml);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


//课程购买
    @RequestMapping(value = "/course/prepay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, String>> coursePrepay(String courseId, String accountId, Double totalFee, String code){
        try{
            Map<String, String> params = wechatMerchantPayService.xcxCoursePrepay(courseId, accountId, totalFee, code);

            return new Response<Map<String, String>>("预支付成功", params);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 支付回调
     */
    @RequestMapping(value = "/course/callback", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_XML_VALUE)
    public String courseCallback(HttpServletRequest request, HttpServletResponse response){
        try{
            String xml = XMLUtils.read(request.getInputStream());

            return wechatMerchantPayService.xcxCourseCallback(xml);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }





//vip购买
    @RequestMapping(value = "/vip/prepay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, String>> vipPrepay(String accountId, Double totalFee, String code){
        try{
            Map<String, String> params = wechatMerchantPayService.xcxVipPrepay(accountId, totalFee, code);

            return new Response<Map<String, String>>("预支付成功", params);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 支付回调
     */
    @RequestMapping(value = "/vip/callback", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_XML_VALUE)
    public String vipCallback(HttpServletRequest request, HttpServletResponse response){
        try{
            String xml = XMLUtils.read(request.getInputStream());

            return wechatMerchantPayService.xcxVipCallback(xml);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }









    /**
     * 情感测试小程序支付
     */
    @RequestMapping(value = "/testVip/prepay", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, String>> testVipPrepay(String accountId, Double totalFee, String code){
        try{
            Map<String, String> params = wechatMerchantPayService.xcxTestVipPrepay(accountId, totalFee, code);

            return new Response<Map<String, String>>("预支付成功", params);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 情感测试小程序支付回调
     */
    @RequestMapping(value = "/testVip/callback", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_XML_VALUE)
    public String testVipCallback(HttpServletRequest request, HttpServletResponse response){
        System.out.println("testVipCallbacktestVipCallbacktestVipCallbacktestVipCallback");
        try{
            String xml = XMLUtils.read(request.getInputStream());

            return wechatMerchantPayService.xcxTestVipCallback(xml);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


}
