package com.lab.hosaily.core.sell.webservice;

import com.lab.hosaily.core.course.entity.Advisor;
import com.lab.hosaily.core.sell.service.SalesService;
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
@RequestMapping(value = "/api/1.0/sales")
public class SalesRestController{

    private static Logger logger = LoggerFactory.getLogger(SalesRestController.class);

    @Autowired
    private SalesService salesService;

    @RequestMapping(value = "/verify/{wechat}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Advisor> verifyAdvisor(@PathVariable("wechat") String wechat, String organizationToken){
        try{
            Advisor advisor = salesService.verifyAdvisor(wechat, organizationToken);
            return new Response<Advisor>("查询成功", advisor);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
