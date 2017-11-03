package com.lab.hosaily.core.sell.webservice;

import com.lab.hosaily.core.sell.entity.AccountCourse;
import com.lab.hosaily.core.sell.service.AccountCourseService;
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
@RequestMapping(value = "/api/1.0/accountCourse")
public class AccountCourseRestController{

    private static Logger logger = LoggerFactory.getLogger(AccountCourseRestController.class);

    @Autowired
    private AccountCourseService accountCourseService;

    /**
     * 添加
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<AccountCourse> save(@RequestBody AccountCourse accountCourse){
        try{
            accountCourseService.save(accountCourse);

            return new Response<AccountCourse>("添加成功", accountCourse);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<AccountCourse> update(@RequestBody AccountCourse accountCourse){
        try{
            accountCourseService.update(accountCourse);

            return new Response<AccountCourse>("修改成功", accountCourse);
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
            accountCourseService.delete(id);

            return new Response<AccountCourse>("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<AccountCourse>> page(Long pageNum, Long pageSize, String accountId){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<AccountCourse> page = accountCourseService.page(pageRequest, accountId);

            return new Response<Page<AccountCourse>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
