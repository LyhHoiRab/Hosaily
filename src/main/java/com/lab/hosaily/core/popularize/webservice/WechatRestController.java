package com.lab.hosaily.core.popularize.webservice;

import com.lab.hosaily.core.popularize.entity.Wechat;
import com.lab.hosaily.core.popularize.service.WechatService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;

@RestController
@RequestMapping(value = "/api/1.0/popularize/wechat")
public class WechatRestController{

    private Logger logger = LoggerFactory.getLogger(WechatRestController.class);

    @Autowired
    private WechatService wechatService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response save(@RequestBody Wechat wechat){
        try{
            wechatService.save(wechat);

            return new Response("保存成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response update(@RequestBody Wechat wechat){
        try{
            wechatService.update(wechat);

            return new Response("更新成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Wechat> getById(@PathVariable("id") String id){
        try{
            Wechat wechat = wechatService.getById(id);

            return new Response<Wechat>("查询成功", wechat);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Wechat>> page(Long pageNum, Long pageSize, String organizationId, String wxno, String nickname, String remark,
                                       String seller, String advisorId){
        try{
            PageRequest  pageRequest = new PageRequest(pageNum, pageSize);
            Page<Wechat> page        = wechatService.page(pageRequest, organizationId, wxno, nickname, remark, seller, advisorId);

            return new Response<Page<Wechat>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file){
        try{
            String url = wechatService.upload(file);
            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
