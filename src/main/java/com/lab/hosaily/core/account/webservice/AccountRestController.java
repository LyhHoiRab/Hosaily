package com.lab.hosaily.core.account.webservice;

import com.rab.babylon.commons.security.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/1.0/account")
public class AccountRestController{

    private static Logger logger = LoggerFactory.getLogger(AccountRestController.class);

    /**
     * 网站应用注册
     */
    @RequestMapping(value = "/register/web", method = RequestMethod.GET)
    public void registerByWeb(HttpServletRequest request, String code, String state){
        try{
            String sessionId = request.getSession().getId();

            
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
