package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.AccountLevel;
import com.lab.hosaily.core.course.service.AccountLevelService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/1.0/accountLevel")
public class AccountLevelRestController{

    private Logger logger = LoggerFactory.getLogger(AccountLevelRestController.class);

    @Autowired
    private AccountLevelService accountLevelService;

    @RequestMapping(value = "/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private Response<AccountLevel> getByAccountId(String accountId){
        try{
            AccountLevel level = accountLevelService.getByAccountId(accountId);

            return new Response<AccountLevel>("查询成功", level);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
