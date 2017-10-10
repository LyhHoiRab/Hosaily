package com.lab.hosaily.core.course.controller;

import com.lab.hosaily.core.course.entity.Advisor;
import com.lab.hosaily.core.course.entity.Tag;
import com.lab.hosaily.core.course.service.AdvisorService;
import com.lab.hosaily.core.course.service.TagService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/page/backstage/course")
public class CourseController{

    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private AdvisorService advisorService;

    @Autowired
    private TagService tagService;



//    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView index(ModelMap content){
//        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//
//            content.put("states", states);
//
//            return new ModelAndView("backstage/course/index", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }
//
//    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView add(ModelMap content){
//        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//            List<Media> medias = mediaService.findByState(UsingState.NORMAL);
//            List<Advisor> advisors = advisorService.findByState(UsingState.NORMAL);
//            List<Tag> tags = tagService.findByState(UsingState.NORMAL);
//            List<Level> levels = levelService.findByState(UsingState.NORMAL);
//
//            content.put("states", states);
//            content.put("medias", medias);
//            content.put("advisors", advisors);
//            content.put("tags", tags);
//            content.put("levels", levels);
//
//            return new ModelAndView("backstage/course/edit", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }
//
//    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView edit(@PathVariable("id") String id, ModelMap content){
//        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//            List<Media> medias = mediaService.findByState(UsingState.NORMAL);
//            List<Advisor> advisors = advisorService.findByState(UsingState.NORMAL);
//            List<Tag> tags = tagService.findByState(UsingState.NORMAL);
//            List<Level> levels = levelService.findByState(UsingState.NORMAL);
//
//            content.put("states", states);
//            content.put("medias", medias);
//            content.put("advisors", advisors);
//            content.put("tags", tags);
//            content.put("levels", levels);
//            content.put("id", id);
//
//            return new ModelAndView("backstage/course/edit", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 添加子课程
//     */
//    @RequestMapping(value = "/add/{parentId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView addChildren(@PathVariable("parentId") String parentId, ModelMap content){
//        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//            List<Media> medias = mediaService.findByState(UsingState.NORMAL);
//            List<Advisor> advisors = advisorService.findByState(UsingState.NORMAL);
//            List<Tag> tags = tagService.findByState(UsingState.NORMAL);
//            List<Level> levels = levelService.findByState(UsingState.NORMAL);
//
//            content.put("states", states);
//            content.put("medias", medias);
//            content.put("advisors", advisors);
//            content.put("tags", tags);
//            content.put("levels", levels);
//            content.put("parentId", parentId);
//
//            return new ModelAndView("backstage/course/edit", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }
}
