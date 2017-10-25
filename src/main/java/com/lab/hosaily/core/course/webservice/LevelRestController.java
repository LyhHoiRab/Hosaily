package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Level;
import com.lab.hosaily.core.course.service.LevelService;
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
@RequestMapping(value = "/api/1.0/level")
public class LevelRestController{

    private static Logger logger = LoggerFactory.getLogger(LevelRestController.class);

    @Autowired
    private LevelService levelService;

    /**
     * 添加记录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Level> save(@RequestBody Level level){
        try{
            levelService.save(level);
            return new Response<Level>("添加成功", level);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Level> update(@RequestBody Level level){
        try{
            levelService.update(level);
            return new Response<Level>("修改成功", level);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Level> getById(@PathVariable("id") String id){
        try{
            Level level = levelService.getById(id);
            return new Response<Level>("查询成功", level);
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
            String url = levelService.upload(file);
            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Level>> page(Long pageNum, Long pageSize, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Level> page = levelService.page(pageRequest, state, createTime, minCreateTime, maxCreateTime);

            return new Response<Page<Level>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询所有可购买的等级记录
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Level>> find(){
        try{
            List<Level> list = levelService.findByState(UsingState.NORMAL);
            return new Response<List<Level>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
