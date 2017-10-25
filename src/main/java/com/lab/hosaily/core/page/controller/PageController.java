package com.lab.hosaily.core.page.controller;

import com.lab.hosaily.commons.consts.SessionConsts;
import com.lab.hosaily.core.account.entity.Attention;
import com.lab.hosaily.core.account.service.AttentionService;
import com.lab.hosaily.core.account.service.UserService;
import com.lab.hosaily.core.course.entity.Comment;
import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.entity.Level;
import com.lab.hosaily.core.course.entity.Tag;
import com.lab.hosaily.core.course.service.*;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.UsingState;
import org.bouncycastle.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/page/h5")
public class PageController{

    private static Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AttentionService attentionService;

    @Autowired
    private PostService postService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TagService tagService;

    @Autowired
    private LevelService levelService;

    /**
     * 首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            return new ModelAndView("web/index", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 发现页面
     */
    @RequestMapping(value = "/discover", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView discover(ModelMap content){
        try{
            return new ModelAndView("web/discover", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 发现详情页
     */
    @RequestMapping(value = "/discover/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView discoverDetail(@PathVariable("id") String id, ModelMap content){
        try{
            Course course = postService.getById(id);

            content.put("course", course);

            return new ModelAndView("web/discoverDetail", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 课程页面
     */
    @RequestMapping(value = "/course", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView course(ModelMap content){
        try{
            List<Tag> tags = tagService.findByState(UsingState.NORMAL);

            content.put("tags", tags);

            return new ModelAndView("web/course", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 课程详情页
     */
    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView courseDetail(@PathVariable("id") String id, ModelMap content){
        try{
            List<Level> levels = levelService.findByState(UsingState.NORMAL);
            Course course = courseService.getCourseById(id);

            content.put("levels", levels);
            content.put("course", course);

            return new ModelAndView("web/courseDetail", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 章节详情页
     */
    @RequestMapping(value = "/chapter/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView chapterDetail(@PathVariable("id") String id, ModelMap content){
        try{
            return new ModelAndView("web/chapterDetail", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 课时详情页
     */
    @RequestMapping(value = "/section/{id}", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView sectionDetail(@PathVariable("id") String id, String chapterId, ModelMap content){
        try{
            Course section = courseService.getSectionById(id);
            Course chapter = courseService.getChapterById(chapterId);
            Course course = courseService.getCourseById(chapter.getParentId());

            content.put("section", section);
            content.put("chapter", chapter);
            content.put("course", course);

            return new ModelAndView("web/section", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 撩妹技巧测试
     */
    @RequestMapping(value = "/skillTest", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView skillTest(ModelMap content){
        try{
            return new ModelAndView("web/skillTest", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 团队介绍
     */
    @RequestMapping(value = "/teamIntroduce", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView teamIntroduce(ModelMap content){
        try{
            return new ModelAndView("web/teamIntroduce", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 俱乐部介绍
     */
    @RequestMapping(value = "/clubIntroduce", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView clubIntroduce(ModelMap content){
        try{
            return new ModelAndView("web/clubIntroduce", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 用户信息
     */
    @RequestMapping(value = "/personalCenter", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView personalCenter(HttpServletRequest request, ModelMap content){
        try{
            String accountId = (String) request.getSession().getAttribute(SessionConsts.ACCOUNT_ID);
            User user = userService.getCacheByAccountId(accountId);
            List<Attention> tracks = attentionService.findTrackByAccountId(accountId);
            List<Attention> collects = attentionService.findCollectByAccountId(accountId);

            content.put("user", user);
            content.put("tracks", tracks);
            content.put("collects", collects);

            return new ModelAndView("web/personalCenter", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
