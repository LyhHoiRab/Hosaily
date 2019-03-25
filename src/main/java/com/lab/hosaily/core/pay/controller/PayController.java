package com.lab.hosaily.core.pay.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/page/pay")
public class PayController{

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView router(){
        return new ModelAndView("pay/router");
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(){
        return new ModelAndView("pay/index");
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView success(){
        return new ModelAndView("pay/success");
    }
}
