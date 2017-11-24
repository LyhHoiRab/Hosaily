package com.lab.hosaily.core.organization.webservice;

import com.lab.hosaily.core.organization.entity.Organization;
import com.lab.hosaily.core.organization.service.OrganizationService;
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
@RequestMapping(value = "/api/1.0/organization")
public class OrganizationRestController{

    private static Logger logger = LoggerFactory.getLogger(OrganizationRestController.class);

    @Autowired
    private OrganizationService organizationService;

    /**
     * 保存
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> save(@RequestBody Organization organization){
        try{
            organizationService.save(organization);

            return new Response<Organization>("添加成功", organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> update(@RequestBody Organization organization){
        try{
            organizationService.update(organization);

            return new Response<Organization>("修改成功", organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Organization> getById(@PathVariable("id") String id){
        try{
            Organization organization = organizationService.getById(id);

            return new Response<Organization>("查询成功", organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Organization>> page(Long pageNum, Long pageSize, String token, String name, UsingState state){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Organization> page = organizationService.page(pageRequest, name, token, state);

            return new Response<Page<Organization>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Organization>> list(String name, String token, UsingState state){
        try{
            List<Organization> list = organizationService.list(name, token, state);

            return new Response<List<Organization>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
