package com.lab.hosaily.core.application.controller;

import com.rab.babylon.commons.security.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page/backstage/webResource")
public class WebResourceController{

    private static Logger logger = LoggerFactory.getLogger(WebResourceController.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            return new ModelAndView("backstage/webResource/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content){
        try{
            return new ModelAndView("backstage/webResource/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(@PathVariable("id") String id, ModelMap content){
        try{
            content.put("id", id);

            return new ModelAndView("backstage/webResource/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
