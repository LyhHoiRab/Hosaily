package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.application.entity.Application;
import com.lab.hosaily.core.course.entity.Advisor;
import com.lab.hosaily.core.course.service.AdvisorService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/advisor")
public class AdvisorRestController{

    private static Logger logger = LoggerFactory.getLogger(AdvisorRestController.class);

    @Autowired
    private AdvisorService advisorService;

    /**
     * 添加导师
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Advisor> save(@RequestBody Advisor advisor){
        try{
            advisorService.save(advisor);
            return new Response<Advisor>("添加成功", advisor);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改导师
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Advisor> update(@RequestBody Advisor advisor){
        try{
            advisorService.update(advisor);
            return new Response<Advisor>("修改成功", advisor);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询导师
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Advisor> getById(@PathVariable("id") String id){
        try{
            Advisor advisor = advisorService.getById(id);
            return new Response<Advisor>("查询成功", advisor);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Advisor>> page(Long pageNum, Long pageSize, String nickname, String name, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Advisor> page = advisorService.page(pageRequest, nickname, name, state, createTime, minCreateTime, maxCreateTime);

            return new Response<Page<Advisor>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Advisor>> list(String nickname, String name, UsingState state, Date createTime){
        try{
            List<Advisor> list = advisorService.list(nickname, name, state, createTime);

            return new Response<List<Advisor>>("查询成功", null);
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
            String url = advisorService.upload(file);
            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
