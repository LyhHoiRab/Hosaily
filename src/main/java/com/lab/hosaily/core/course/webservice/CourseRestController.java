package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.service.CourseService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController
@RequestMapping(value = "/api/1.0/course")
public class CourseRestController{

    private static Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    @Autowired
    private CourseService courseService;

    /**
     * 分页查询帖子记录
     */
    @RequestMapping(value = "/page/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Course>> pageByPost(Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Course> page = courseService.pageByPost(pageRequest);

            return new Response<Page<Course>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询课程记录
     */
    @RequestMapping(value = "/page/course", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Course>> pageByCourse(Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Course> page = courseService.pageByCourse(pageRequest);

            return new Response<Page<Course>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * H5分页查询课程记录
     */
    @RequestMapping(value = "/page/h5/course", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Course>> pageByH5AndCourse(Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Course> page = courseService.pageByH5AndCourse(pageRequest);

            return new Response<Page<Course>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * H5分页查询帖子记录
     */
    @RequestMapping(value = "/page/h5/post", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Course>> pageByH5AndPost(Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Course> page = courseService.pageByH5AndPost(pageRequest);

            return new Response<Page<Course>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file){
        try{
            String url = courseService.upload(file);
            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
