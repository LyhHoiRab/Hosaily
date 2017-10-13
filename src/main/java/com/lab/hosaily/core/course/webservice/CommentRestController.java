package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Comment;
import com.lab.hosaily.core.course.service.CommentService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/1.0/comment")
public class CommentRestController{

    private static Logger logger = LoggerFactory.getLogger(CommentRestController.class);

    @Autowired
    private CommentService commentService;

    /**
     * 添加评论
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Comment> save(@RequestBody Comment comment){
        try{
            commentService.save(comment);

            return new Response<Comment>("添加成功", comment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新评论
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Comment> update(@RequestBody Comment comment){
        try{
            commentService.update(comment);

            return new Response<Comment>("更新成功", comment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据课程ID分页查询
     */
    @RequestMapping(value = "/page/course/{courseId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Comment>> pageByCourseId(@PathVariable("courseId") String courseId, Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Comment> page = commentService.pageByCourseId(pageRequest, courseId);

            return new Response<Page<Comment>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据用户ID分页查询
     */
    @RequestMapping(value = "/page/sender/{senderId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Comment>> pageBySenderId(@PathVariable("senderId") String senderId, Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Comment> page = commentService.pageByCourseId(pageRequest, senderId);

            return new Response<Page<Comment>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
