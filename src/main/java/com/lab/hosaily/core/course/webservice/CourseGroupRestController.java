package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.CourseGroup;
import com.lab.hosaily.core.course.service.CourseGroupService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/courseGroup")
public class CourseGroupRestController{

    private static Logger logger = LoggerFactory.getLogger(CourseGroupRestController.class);

    @Autowired
    private CourseGroupService courseGroupService;

    /**
     * 添加
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<CourseGroup> save(@RequestBody CourseGroup group){
        try{
            courseGroupService.save(group);

            return new Response<CourseGroup>("添加成功", group);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<CourseGroup> update(@RequestBody CourseGroup group){
        try{
            courseGroupService.update(group);

            return new Response<CourseGroup>("修改成功", group);
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
            courseGroupService.delete(id);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @RequestMapping(value = "page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<CourseGroup>> page(Long pageNum, Long pageSize, String groupId, String organizationId, String organizationToken, UsingState state, String name){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<CourseGroup> page = courseGroupService.page(pageRequest, groupId, organizationId, organizationToken, state, name);

            return new Response<Page<CourseGroup>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<CourseGroup>> list(String accountId, String groupId, String organizationId, String organizationToken, UsingState state, String name){
        try{
            List<CourseGroup> list = courseGroupService.list(accountId, groupId, organizationId, organizationToken, state, name);

            return new Response<List<CourseGroup>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<CourseGroup> getById(@PathVariable("id") String id){
        try{
            CourseGroup group = courseGroupService.getById(id);

            return new Response<CourseGroup>("查询成功", group);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 课程组授权
     */
    @RequestMapping(value = "/authorization/{accountId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response authorization(@PathVariable("accountId") String accountId, @RequestBody List<CourseGroup> groups){
        try{
            courseGroupService.authorization(accountId, groups);

            return new Response("授权成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 是否有授权课程
     */
    @RequestMapping(value = "/hasCourse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> hasCourse(String accountId, String courseId){
        try{
            Boolean result = courseGroupService.hasCourse(accountId, courseId);

            return new Response<Boolean>("查询成功", result);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
