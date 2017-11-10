package com.lab.hosaily.core.account.webservice;

import com.lab.hosaily.commons.consts.SessionConsts;
import com.lab.hosaily.core.account.service.UserService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/user")
public class UserRestController{

    private static Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    /**
     * 添加用户
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<User> save(@RequestBody User user){
        try{
            userService.save(user);
            return new Response<User>("添加成功", user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<User> update(@RequestBody User user){
        try{
            userService.update(user);
            return new Response<User>("修改成功", user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据accountId修改用户
     */
    @RequestMapping(value = "/h5", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<User> updateByH5(HttpServletRequest request, @RequestBody User user){
        try{
            String accountId = (String) request.getSession().getAttribute(SessionConsts.ACCOUNT_ID);

            user.setAccountId(accountId);
            userService.updateByH5(user);

            return new Response<User>("修改成功", user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询用户
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<User> getById(@PathVariable("id") String id){
        try{
            User user = userService.getById(id);
            return new Response<User>("查询成功", user);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 上传头像
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file){
        try{
            String url = userService.upload(file);
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
    public Response<Page<User>> page(Long pageNum, Long pageSize, String accountId, UsingState state, String wechat, String nickname, String name, Integer code){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<User> page = userService.page(pageRequest, accountId, state, wechat, nickname, name, code, null);

            return new Response<Page<User>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<User>> list(String accountId, UsingState state, String wechat, String nickname, String name, Integer code){
        try{
            List<User> list = userService.list(accountId, state, wechat, nickname, name, code, null);

            return new Response<List<User>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
