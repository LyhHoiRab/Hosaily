package com.lab.hosaily.core.popularize.controller;

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
@RequestMapping(value = "/page/backstage/wechat")
public class WechatController{

    private static Logger logger = LoggerFactory.getLogger(WechatController.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            return new ModelAndView("backstage/wechat/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/list/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView list(@PathVariable("organizationId") String organizationId, ModelMap content){
        try{
            content.put("organizationId", organizationId);

            return new ModelAndView("backstage/wechat/list", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/add/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(@PathVariable("organizationId") String organizationId, ModelMap content){
        try{
            content.put("organizationId", organizationId);

            return new ModelAndView("backstage/wechat/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(@PathVariable("id") String id, ModelMap content){
        try{
            content.put("id", id);

            return new ModelAndView("backstage/wechat/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
