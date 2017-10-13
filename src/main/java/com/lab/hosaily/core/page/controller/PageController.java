package com.lab.hosaily.core.page.controller;

import com.rab.babylon.commons.security.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page")
public class PageController{

    private static Logger logger = LoggerFactory.getLogger(PageController.class);

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            return new ModelAndView("web/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/qr", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView qr(ModelMap content){
        try{
            return new ModelAndView("web/qr", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
