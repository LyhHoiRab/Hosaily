package com.lab.hosaily.core.client.controller;

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
@RequestMapping(value = "/page/platform/purchase")
public class PurchaseController{

    private static Logger logger = LoggerFactory.getLogger(PurchaseController.class);

//    @RequestMapping(value = "/add/{accountId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView add(@PathVariable("accountId") String accountId, ModelMap content){
//        try{
//            content.put("accountId", accountId);
//
//            return new ModelAndView("platform/client/purchase", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }
//
//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView edit(@PathVariable("id") String id, ModelMap content){
//        try{
//            content.put("id", id);
//
//            return new ModelAndView("platform/client/purchase", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }
//
//    @RequestMapping(value = "/list/{accountId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView list(@PathVariable("accountId") String accountId, ModelMap content){
//        try{
//            content.put("accountId", accountId);
//
//            return new ModelAndView("platform/client/purchaseList", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            return new ModelAndView("platform/purchase/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/list/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView list(ModelMap content){
        try{
            return new ModelAndView("platform/purchase/list", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/add/{organizationId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content){
        try{
            return new ModelAndView("platform/purchase/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(ModelMap content){
        try{
            return new ModelAndView("platform/purchase/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
