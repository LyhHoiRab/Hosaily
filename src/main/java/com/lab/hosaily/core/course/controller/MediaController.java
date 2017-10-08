package com.lab.hosaily.core.course.controller;

import com.lab.hosaily.core.course.consts.SuffixType;
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
@RequestMapping(value = "/page/backstage/media")
public class MediaController{

    private static Logger logger = LoggerFactory.getLogger(MediaController.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            List<com.lab.hosaily.core.course.consts.MediaType> types = Arrays.asList(com.lab.hosaily.core.course.consts.MediaType.values());
            List<UsingState> states = Arrays.asList(UsingState.values());
            List<SuffixType> suffixs = Arrays.asList(SuffixType.values());

            content.put("types", types);
            content.put("states", states);
            content.put("suffixs", suffixs);

            return new ModelAndView("backstage/media/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content){
        try{
            List<com.lab.hosaily.core.course.consts.MediaType> types = Arrays.asList(com.lab.hosaily.core.course.consts.MediaType.values());
            List<UsingState> states = Arrays.asList(UsingState.values());
            List<SuffixType> suffixs = Arrays.asList(SuffixType.values());

            content.put("types", types);
            content.put("states", states);
            content.put("suffixs", suffixs);

            return new ModelAndView("backstage/media/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content, @PathVariable("id") String id){
        try{
            List<com.lab.hosaily.core.course.consts.MediaType> types = Arrays.asList(com.lab.hosaily.core.course.consts.MediaType.values());
            List<UsingState> states = Arrays.asList(UsingState.values());
            List<SuffixType> suffixs = Arrays.asList(SuffixType.values());

            content.put("types", types);
            content.put("states", states);
            content.put("suffixs", suffixs);
            content.put("id", id);

            return new ModelAndView("backstage/media/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
