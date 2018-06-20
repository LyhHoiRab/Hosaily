package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.Option;
import com.lab.hosaily.core.course.service.OptionService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RestController
@RequestMapping(value = "/api/1.0/option")
public class OptionRestController {

    private static Logger logger = LoggerFactory.getLogger(OptionRestController.class);

    @Autowired
    private OptionService optionService;

    /**
     * 保存记录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Option> save(@RequestBody Option option){
        try{
            optionService.save(option);
            return new Response<Option>("添加成功", option);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Option> update(@RequestBody Option option){
        try{
            optionService.update(option);
            return new Response<Option>("修改成功", option);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Option> getById(@PathVariable("id") String id){
        try{
            Option option = optionService.getById(id);
            return new Response<Option>("查询成功", option);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Option>> page(Long pageNum, Long pageSize, String title, String projectId, String questionId, String questionOption, String organizationId){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Option> page = optionService.page(pageRequest, title, projectId, questionId, questionOption, organizationId);

            return new Response<Page<Option>>("查询成功", page);
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
            optionService.delete(id);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file) {
        try {
            String url = optionService.upload(file);
            return new Response<String>("上传成功", url);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
