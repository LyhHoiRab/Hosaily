package com.lab.hosaily.core.application.webservice;

import com.lab.hosaily.core.application.entity.WebResource;
import com.lab.hosaily.core.application.service.WebResourceService;
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

@RestController
@RequestMapping(value = "/api/1.0/webResource")
public class WebResourceRestController{

    private static Logger logger = LoggerFactory.getLogger(WebResourceRestController.class);

    @Autowired
    private WebResourceService webResourceService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<WebResource> save(@RequestBody WebResource resource){
        try{
            webResourceService.save(resource);

            return new Response<WebResource>("添加成功", resource);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<WebResource> update(@RequestBody WebResource resource){
        try{
            webResourceService.update(resource);

            return new Response<WebResource>("修改成功", resource);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 刷新缓存
     */
    @RequestMapping(value = "/refresh", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response updateCache(){
        try{
            webResourceService.updateCache();

            return new Response("刷新成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<WebResource> getById(@PathVariable("id") String id){
        try{
            WebResource resource = webResourceService.getById(id);

            return new Response<WebResource>("查询成功", resource);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<WebResource>> page(Long pageNum, Long pageSize, UsingState state, String domain, String imgUrl, String cssUrl, String jsUrl, String organizationId, String organizationToken){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<WebResource> page = webResourceService.page(pageRequest, state, domain, imgUrl, cssUrl, jsUrl, organizationId, organizationToken);

            return new Response<Page<WebResource>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
