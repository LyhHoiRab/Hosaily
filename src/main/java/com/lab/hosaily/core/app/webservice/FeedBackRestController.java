package com.lab.hosaily.core.app.webservice;

import com.lab.hosaily.core.app.entity.Customer;
import com.lab.hosaily.core.app.entity.FeedBack;
import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.app.service.CustomerService;
import com.lab.hosaily.core.app.service.FeedBackService;
import com.lab.hosaily.core.app.service.ProfileService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/13.
 */
@RestController
@RequestMapping(value = "/api/1.0/app/feedBack")
@Api(description = "客户动态资料")
public class FeedBackRestController {

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CustomerService customerService;

    //    http://localhost:8080/swagger-ui.html
    private static final Logger logger = LoggerFactory.getLogger(FeedBackRestController.class);

    @ApiIgnore
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<FeedBack> save() {
        try {
            FeedBack feedBack = new FeedBack();
            feedBack.setOrganizationId("setOrganizationId");
            feedBack.setComment("setComment");
            feedBack.setPicUrls("setPicUrls");
            feedBack.setCustomerId("setProfileId");
            feedBackService.save(feedBack);
            return new Response<FeedBack>("添加成功", feedBack);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiIgnore
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<FeedBack> update() {
        try {
            FeedBack feedBack = feedBackService.getById("4a60f582212442f68832a8dbe6ec0b5b");
            feedBack.setOrganizationId("setOrganizationId1");
            feedBack.setComment("setComment1");
            feedBack.setPicUrls("setPicUrls1");
            feedBack.setCustomerId("setProfileId1");
            feedBackService.update(feedBack);

            return new Response<FeedBack>("修改成功", feedBack);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<FeedBack> getById(@PathVariable("id") String id) {
        try {
            FeedBack feedBack = feedBackService.getById(id);
            return new Response<FeedBack>("查询成功", feedBack);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "客户资料", notes = "在这里可以获取分页获取客户朋友圈动态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "profileId", value = "爱情档案ID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<FeedBack>> page(Long pageNum, Long pageSize, String customerId) {
        try {
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<FeedBack> page = feedBackService.page(pageRequest, customerId);

            return new Response<Page<FeedBack>>("查询成功", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }



    @ApiOperation(value = "提交上传客户资料", notes = "图片url用';'号隔开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "picUrls", value = "图片url用';'分隔", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "comment", value = "备注文本", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "uploaderId", value = "上传者profileID", paramType = "query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "profileId", value = "当前浏览客户爱情档案ID", paramType = "query", required = true, dataType = "String")
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(String picUrls, String comment, String uploaderId, String customerId, String status) {
        try {
            Response<String> response = new Response<String>();


            if(StringUtils.isBlank(uploaderId)){
                response.setSuccess(false);
                response.setMsg("uploaderId不能为空！");
                return response;
            }
            if(StringUtils.isBlank(customerId)){
                response.setSuccess(false);
                response.setMsg("customerId不能为空！");
                return response;
            }
            if(StringUtils.isBlank(status)){
                response.setSuccess(false);
                response.setMsg("状态不能为空！");
                return response;
            }

            FeedBack feedBack = new FeedBack();
            feedBack.setPicUrls(picUrls);
            feedBack.setCustomerId(customerId);
            feedBack.setComment(comment);
            feedBack.setStatus(status);
            Profile profile = profileService.getById(uploaderId);
            System.out.println("aaaaaaaaaaaaaaa: " + profile.getName());
            feedBack.setUploader(profile);
            feedBackService.save(feedBack);


//            上传过反馈的客户将自动更新成已处理客户
            Customer customer = customerService.getById(customerId);
            customer.setSituation("1");
            customer.setProcessTime(new Date());
            customer.setProcess(status);
            customerService.update(customer);
            
            response.setSuccess(true);
            response.setMsg("提交成功");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
