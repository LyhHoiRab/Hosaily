package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Customization;
import com.lab.hosaily.core.course.service.CustomizationService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/1.0/customization")
public class CustomizationRestController{

    private static Logger logger = LoggerFactory.getLogger(CustomizationRestController.class);

    @Autowired
    private CustomizationService customizationService;

    /**
     * 添加
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Customization> save(@RequestBody Customization customization){
        try{
            customizationService.save(customization);

            return new Response<Customization>("添加成功", customization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Customization> update(@RequestBody Customization customization){
        try{
            customizationService.update(customization);

            return new Response<Customization>("修改成功", customization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Customization> getById(@PathVariable("id") String id){
        try{
            Customization customization = customizationService.getById(id);

            return new Response<Customization>("查询成功", customization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Customization>> page(Long pageNum, Long pageSize, UsingState state, String tagName, String organizationId, String organizationToken){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Customization> page = customizationService.page(pageRequest, state, tagName, organizationId, organizationToken);

            return new Response<Page<Customization>>("查询成功", page);
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
            String url = customizationService.upload(file);

            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
