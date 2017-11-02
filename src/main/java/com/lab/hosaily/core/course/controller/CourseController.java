package com.lab.hosaily.core.course.controller;

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
@RequestMapping(value = "/page/backstage/course")
public class CourseController{

    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            return new ModelAndView("backstage/course/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView add(ModelMap content){
        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//            List<Advisor> advisors = advisorService.findByState(UsingState.NORMAL);
//            List<Tag> tags = tagService.findByState(UsingState.NORMAL);
//            List<Level> levels = levelService.findByState(UsingState.NORMAL);
//
//            content.put("states", states);
//            content.put("advisors", advisors);
//            content.put("tags", tags);
//            content.put("levels", levels);

            return new ModelAndView("backstage/course/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView edit(@PathVariable("id") String id, ModelMap content){
        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//            List<Advisor> advisors = advisorService.findByState(UsingState.NORMAL);
//            List<Tag> tags = tagService.findByState(UsingState.NORMAL);
//            List<Level> levels = levelService.findByState(UsingState.NORMAL);

//            content.put("states", states);
//            content.put("advisors", advisors);
//            content.put("tags", tags);
//            content.put("levels", levels);
            content.put("id", id);

            return new ModelAndView("backstage/course/edit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 添加章节
     */
    @RequestMapping(value = "/add/chapter/{parentId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addChapter(@PathVariable("parentId") String parentId, ModelMap content){
        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//
//            content.put("states", states);
            content.put("parentId", parentId);

            return new ModelAndView("backstage/course/chapterEdit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改章节
     */
    @RequestMapping(value = "/edit/chapter/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editChapter(@PathVariable("id") String id, ModelMap content){
        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//
//            content.put("states", states);
            content.put("id", id);

            return new ModelAndView("backstage/course/chapterEdit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 添加课时
     */
    @RequestMapping(value = "/add/section/{parentId}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addSection(@PathVariable("parentId") String parentId, ModelMap content){
        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//            List<Media> medias = mediaService.findByState(UsingState.NORMAL);
//
//            content.put("states", states);
//            content.put("medias", medias);
            content.put("parentId", parentId);

            return new ModelAndView("backstage/course/sectionEdit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改课时
     */
    @RequestMapping(value = "/edit/section/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editSection(@PathVariable("id") String id, ModelMap content){
        try{
//            List<UsingState> states = Arrays.asList(UsingState.values());
//            List<Media> medias = mediaService.findByState(UsingState.NORMAL);
//
//            content.put("states", states);
//            content.put("medias", medias);
            content.put("id", id);

            return new ModelAndView("backstage/course/sectionEdit", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
