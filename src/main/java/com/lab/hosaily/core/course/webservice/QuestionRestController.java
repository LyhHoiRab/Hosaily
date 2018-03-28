package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Option;
import com.lab.hosaily.core.course.entity.Question;
import com.lab.hosaily.core.course.service.OptionService;
import com.lab.hosaily.core.course.service.QuestionService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/question")
public class QuestionRestController {

    private static Logger logger = LoggerFactory.getLogger(QuestionRestController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionService optionService;

    /**
     * 保存记录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Question> save(@RequestBody Question question){
        try{
            questionService.save(question);
            return new Response<Question>("添加成功", question);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Question> update(@RequestBody Question question){
        try{
            questionService.update(question);
            return new Response<Question>("修改成功", question);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Question> getById(@PathVariable("id") String id){
        try{
            Question question = questionService.getById(id);
            return new Response<Question>("查询成功", question);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Question>> page(Long pageNum, Long pageSize, String num, String title, String projectId, String organizationId){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Question> page = questionService.page(pageRequest, num, title, projectId, organizationId);

            return new Response<Page<Question>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response delete(@PathVariable("id") String id){
        try{
            questionService.delete(id);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Question>> list(String nickname, String name, UsingState state, String organizationId, String organizationToken){
        try{
            List<Question> list = questionService.list(nickname, name, state, organizationId, organizationToken);

            return new Response<List<Question>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }



    /**
     * 根据测试项目id 查找测试项目的第一道测试题目
     */
    @RequestMapping(value = "/getFirstQuestion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, Object>> getFirstQuestion(String projectId, String num, UsingState state, String organizationId, String questionId){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            Question question = questionService.getFirstQuestion(projectId, num, state, organizationId, questionId);
            map.put("question", question);
            List<Option> options = optionService.list(question.getId());
            map.put("options", options);
            return new Response<Map<String, Object>>("查询成功", map);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据N道题的选项查找相应的下一道题目
     */
    @RequestMapping(value = "/getNextQuestion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, Object>> getNextQuestion(String projectId, String num, UsingState state, String organizationId, String questionId){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            Question question = questionService.getNextQuestion(projectId, num, state, organizationId, questionId);
            map.put("question", question);
            List<Option> options = optionService.list(question.getId());
            map.put("options", options);
            return new Response<Map<String, Object>>("查询成功", map);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
