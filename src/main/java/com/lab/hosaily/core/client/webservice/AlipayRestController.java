package com.lab.hosaily.core.client.webservice;

import com.lab.hosaily.core.client.service.AlipayService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import org.bouncycastle.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/alipay")
public class AlipayRestController{

    private static Logger logger = LoggerFactory.getLogger(AlipayRestController.class);

    @Autowired
    private AlipayService alipayService;

    /**
     * 支付
     */
    @RequestMapping(value = "/pay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void pay(HttpServletRequest request, HttpServletResponse response, String purchaseId, Double totalFee){
        try{
            String form = alipayService.pay(purchaseId, totalFee);

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(form);
            response.getWriter().flush();
            response.getWriter().close();
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 回调
     */
    @RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String callback(HttpServletRequest request){
        try{
            Map<String, String> params = new HashMap<String, String>();

            Map requestParams = request.getParameterMap();
            for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";

                for(int i = 0; i < values.length; i++){
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }

            return alipayService.callback(params);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
