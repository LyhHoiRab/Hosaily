package com.lab.hosaily.core.pay.webservice;

import com.lab.hosaily.commons.utils.WechatMerchantPayUtils;
import com.lab.hosaily.core.pay.service.PayService;
import com.rab.babylon.commons.security.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/pay")
public class PayRestController{

    @Autowired
    private PayService payService;

    @RequestMapping(value = "/wx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, String>> wxPay(String code, Double totalFee) throws Exception{
        Map<String, String> params = payService.wxPay(code, totalFee);

        return new Response<Map<String, String>>("预支付成功", params);
    }

    @RequestMapping(value = "/wx/callback", method = {RequestMethod.POST, RequestMethod.GET}, produces = MediaType.APPLICATION_XML_VALUE)
    public String wxCallback(HttpServletRequest request, HttpServletResponse response){
        //结果回复
        Map<String, String> params = new HashMap<String, String>();
        params.put("return_code", "SUCCESS");
        params.put("return_msg", "OK");

        return WechatMerchantPayUtils.toXml(params);
    }

    @RequestMapping(value = "/ali", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void alipay(HttpServletRequest request, HttpServletResponse response, Double totalFee) throws Exception{
        String form = payService.alipay(totalFee);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping(value = "/ali/callback", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String aliCallback(HttpServletRequest request){
        return "success";
    }

    @RequestMapping(value = "/ali/return", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public void aliReturn(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.sendRedirect("/page/pay/success");
    }
}
