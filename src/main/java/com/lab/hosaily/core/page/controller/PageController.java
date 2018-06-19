package com.lab.hosaily.core.page.controller;

import com.lab.hosaily.commons.consts.SessionConsts;
import com.lab.hosaily.core.account.entity.Attention;
import com.lab.hosaily.core.account.service.AttentionService;
import com.lab.hosaily.core.account.service.UserService;
import com.lab.hosaily.core.client.entity.Purchase;
import com.lab.hosaily.core.client.service.AgreementService;
import com.lab.hosaily.core.client.service.PurchaseService;
import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.entity.Customization;
import com.lab.hosaily.core.course.entity.Level;
import com.lab.hosaily.core.course.service.CourseService;
import com.lab.hosaily.core.course.service.CustomizationService;
import com.lab.hosaily.core.course.service.LevelService;
import com.lab.hosaily.core.course.service.PostService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.UsingState;
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
    private LevelService levelService;

    @Autowired
    private CustomizationService customizationService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private AgreementService agreementService;

    /**
     * 首页
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView index(ModelMap content){
        try{
            return new ModelAndView("index", content);
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
            return new ModelAndView("discover", content);
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

            return new ModelAndView("discoverDetail", content);
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
            return new ModelAndView("course", content);
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

            return new ModelAndView("courseDetail", content);
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
            return new ModelAndView("chapterDetail", content);
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

            return new ModelAndView("section", content);
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
            return new ModelAndView("skillTest", content);
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
            return new ModelAndView("teamIntroduce", content);
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
            return new ModelAndView("clubIntroduce", content);
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

            return new ModelAndView("personalCenter", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 定制服务页
     */
    @RequestMapping(value = "/customization", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView customization(ModelMap content){
        try{
            return new ModelAndView("customization", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 定制服务详情页
     */
    @RequestMapping(value = "/customization/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView customizationDetail(@PathVariable("id") String id, ModelMap content){
        try{
            Customization customization = customizationService.getById(id);

            content.put("customization", customization);

            return new ModelAndView("customizationDetail", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 验证老师
     */
    @RequestMapping(value = "/checkExper", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView verifyAdvisor(ModelMap content){
        try{
            return new ModelAndView("checkExper", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 测试
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView test(String accountId, String params, ModelMap content){
        try{
            content.put("accountId", accountId);
            content.put("params", params);

            return new ModelAndView("test", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 支付首页
     */
    @RequestMapping(value = "/goPay", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView goPay(String purchaseId, ModelMap content){
        try{
            content.put("purchaseId", purchaseId);

            return new ModelAndView("goPay", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 协议
     */
//    @RequestMapping(value = "/agreement", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//    public ModelAndView agreement(String purchaseId, ModelMap content){
//        try{
//            content.put("purchaseId", purchaseId);
//
//            return new ModelAndView("agreement", content);
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new ApplicationException(e.getMessage(), e);
//        }
//    }

    /**
     * 协议
     */
    @RequestMapping(value = "/agreement", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView agreement(String id, ModelMap content){
        try{
            content.put("id", id);

            return new ModelAndView("agreement", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 支付页面
     */
    @RequestMapping(value = "/pay", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView pay(String purchaseId, String code, ModelMap content){
        try{
            Purchase purchase = purchaseService.getById(purchaseId);
//            purchase.setPurchaseState(PurchaseState.AGREEMENT);
//            purchaseService.update(purchase);

            agreementService.affirm(purchase.getAgreement().getId());

            content.put("purchaseId", purchaseId);
            content.put("code", code);

            return new ModelAndView("pay", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 预约首页
     */
    @RequestMapping(value = "/appointment", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView appointment(ModelMap content){
        try{
            return new ModelAndView("appointment", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 预约基本资料填写
     */
    @RequestMapping(value = "/appointment/info", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView appointmentInfo(ModelMap content){
        try{
            return new ModelAndView("appointmentInfo", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 预约详情填写
     */
    @RequestMapping(value = "/appointment/detail", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView appointmentDetail(ModelMap content){
        try{
            return new ModelAndView("appointmentDetail", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 预约完成页
     */
    @RequestMapping(value = "/appointment/complete", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView appointmentComplete(ModelMap content){
        try{
            return new ModelAndView("appointmentComplete", content);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
