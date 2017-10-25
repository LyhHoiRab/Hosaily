package com.lab.hosaily.core.page.controller;

import com.lab.hosaily.core.page.consts.CommonsPathConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController{

    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 首页
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public void index(HttpServletRequest request, HttpServletResponse response){
        try{
            request.getRequestDispatcher(CommonsPathConsts.H5_INDEX).forward(request,response);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationContextException(e.getMessage(), e);
        }
    }
}
