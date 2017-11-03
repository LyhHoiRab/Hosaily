package com.lab.hosaily.core.sell.controller;

import com.rab.babylon.commons.security.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page/backstage/accountCourse")
public class AccountCourseController{

    private static Logger logger = LoggerFactory.getLogger(AccountCourseController.class);

    /**
     * 账户关联课程列页
     */
    @RequestMapping(value = "/{accountId}")
    public ModelAndView index(@PathVariable("accountId") String accountId, ModelMap content){
        try{
            content.put("accountId", accountId);

            return new ModelAndView("backstage/user/accountCourse", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 添加关联课程页
     */
    @RequestMapping(value = "/add/{accountId}")
    public ModelAndView edit(@PathVariable("accountId") String accountId, ModelMap content){
        try{
            content.put("accountId", accountId);

            return new ModelAndView("backstage/user/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
