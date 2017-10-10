package com.lab.hosaily.core.account.webservice;

import com.lab.hosaily.core.account.service.AccountService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/1.0/account")
public class AccountRestController{

    private static Logger logger = LoggerFactory.getLogger(AccountRestController.class);

    @Autowired
    private AccountService accountService;

    /**
     * 网站应用注册
     */
    @RequestMapping(value = "/register/web", method = RequestMethod.GET)
    public void registerByWeb(HttpServletRequest request, String code, String state){
        try{
            String sessionId = request.getSession().getId();
            //TODO 解析state
            String token = "";
            String redirectUrl = "";
            String contrastId = "";
            //校验
            if(!sessionId.equalsIgnoreCase(contrastId)){
                //TODO校验失败
            }
            accountService.registerByWeb(token, code);

            //TODO 跳转到state指定URL
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
