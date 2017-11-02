package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Tag;
import com.lab.hosaily.core.course.service.TagService;
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

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/tag")
public class TagRestController{

    private static Logger logger = LoggerFactory.getLogger(TagRestController.class);

    @Autowired
    private TagService tagService;

    /**
     * 保存记录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Tag> save(@RequestBody Tag tag){
        try{
            tagService.save(tag);
            return new Response<Tag>("添加成功", tag);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Tag> update(@RequestBody Tag tag){
        try{
            tagService.update(tag);
            return new Response<Tag>("修改成功", tag);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Tag> getById(@PathVariable("id") String id){
        try{
            Tag tag = tagService.getById(id);
            return new Response<Tag>("查询成功", tag);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Tag>> page(Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Tag> page = tagService.page(pageRequest);

            return new Response<Page<Tag>>("查询成功", tagService.page(pageRequest));
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询可用的标签列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Tag>> list(UsingState state){
        try{
            List<Tag> list = tagService.list(state);
            return new Response<List<Tag>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
