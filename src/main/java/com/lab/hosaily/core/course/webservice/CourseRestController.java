package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.service.CourseService;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/course")
public class CourseRestController{

    private static Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    @Autowired
    private CourseService courseService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Course> save(@RequestBody Course course){
        try{
            courseService.save(course);

            return new Response<Course>("添加成功", course);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Course> update(@RequestBody Course course){
        try{
            courseService.update(course);

            return new Response<Course>("修改成功", course);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询课程列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Course>> list(String tagName, String advisor, UsingState state, String accountId, String organizationId, String organizationToken){
        try{
            List<Course> list = courseService.listByCourse(tagName, advisor, state, accountId, organizationId, organizationToken);

            return new Response<List<Course>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询课程
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Course>> page(Long pageNum, Long pageSize, String tagName, String advisor, UsingState state, String accountId, String organizationId, String organizationToken){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Course> page = courseService.pageByCourse(pageRequest, tagName, advisor, state, accountId, organizationId, organizationToken);

            return new Response<Page<Course>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询章节
     */
    @RequestMapping(value = "/page/chapter", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Course>> pageByChapter(Long pageNum, Long pageSize, String parentId, UsingState state){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Course> page = courseService.pageByChapter(pageRequest, parentId, state);

            return new Response<Page<Course>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询课程
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Course> getCourseById(@PathVariable("id") String id){
        try{
            Course course = courseService.getCourseById(id);

            return new Response<Course>("查询成功", course);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询章节
     */
    @RequestMapping(value = "/chapter/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Course> getChapterById(@PathVariable("id") String id){
        try{
            Course course = courseService.getChapterById(id);

            return new Response<Course>("查询成功", course);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询课时
     */
    @RequestMapping(value = "/section/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Course> getSectionById(@PathVariable("id") String id){
        try{
            Course course = courseService.getSectionById(id);

            return new Response<Course>("查询成功", course);
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
