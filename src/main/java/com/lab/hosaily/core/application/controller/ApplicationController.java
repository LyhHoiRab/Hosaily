package com.lab.hosaily.core.application.controller;

import com.lab.hosaily.core.application.consts.ApplicationType;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/page/backstage/application")
public class ApplicationController{

    private static Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            List<UsingState> states = Arrays.asList(UsingState.values());
            List<ApplicationType> types = Arrays.asList(ApplicationType.values());

            content.put("states", states);
            content.put("types", types);

            return new ModelAndView("backstage/application/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content){
        try{
            List<ApplicationType> types = Arrays.asList(ApplicationType.values());
            content.put("types", types);

            return new ModelAndView("backstage/application/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(@PathVariable("id") String id, ModelMap content){
        try{
            List<ApplicationType> types = Arrays.asList(ApplicationType.values());
            content.put("types", types);
            content.put("id", id);

            return new ModelAndView("backstage/application/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
