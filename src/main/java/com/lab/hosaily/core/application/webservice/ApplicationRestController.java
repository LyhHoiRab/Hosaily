package com.lab.hosaily.core.application.webservice;

import com.lab.hosaily.core.application.entity.Application;
import com.lab.hosaily.core.application.service.ApplicationService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/1.0/application")
public class ApplicationRestController{

    private static Logger logger = LoggerFactory.getLogger(ApplicationRestController.class);

    @Autowired
    private ApplicationService applicationService;

    /**
     * 添加应用
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Application> save(@RequestBody Application application){
        try{
            applicationService.save(application);
            return new Response<Application>("添加成功", application);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改应用
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Application> update(@RequestBody Application application){
        try{
            applicationService.update(application);
            return new Response<Application>("修改成功", application);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询网站应用授权二维码参数
     */
    @RequestMapping(value = "/qr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Map<String, Object>> getQRParams(HttpServletRequest request, String token, String redirectUrl){
        try{
            String sessionId = request.getSession().getId();
            Map<String, Object> result = applicationService.getQRParams(sessionId, token, redirectUrl);

            return new Response<Map<String, Object>>("查询成功", result);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Application>> page(Long pageNum, Long pageSize){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Application> page = applicationService.page(pageRequest);

            return new Response<Page<Application>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Application> getById(@PathVariable("id") String id){
        try{
            Application application = applicationService.getById(id);
            return new Response<Application>("查询成功", application);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
