package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.core.course.entity.SimNumUser;
import com.lab.hosaily.core.course.service.SimNumUserService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/1.0/simNumUser")
public class SimNumUserRestController {

    private static Logger logger = LoggerFactory.getLogger(SimNumUserRestController.class);

    @Autowired
    private SimNumUserService simNumUserService;

    /**
     * 保存记录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<SimNumUser> save(@RequestBody SimNumUser simNumUser){
        try{
            simNumUserService.save(simNumUser);
            return new Response<SimNumUser>("添加成功", simNumUser);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<SimNumUser> update(@RequestBody SimNumUser simNumUser){
        try{
            simNumUserService.update(simNumUser);
            return new Response<SimNumUser>("修改成功", simNumUser);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @RequestMapping(value = "/{sim}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<SimNumUser> getById(@PathVariable("sim") String sim){
        try{
            SimNumUser simNumUser = simNumUserService.getBySim(sim);
            return new Response<SimNumUser>("查询成功", simNumUser);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<SimNumUser>> page(Long pageNum, Long pageSize, String num){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<SimNumUser> page = simNumUserService.page(pageRequest, num);

            return new Response<Page<SimNumUser>>("查询成功", page);
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
            simNumUserService.delete(id);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
