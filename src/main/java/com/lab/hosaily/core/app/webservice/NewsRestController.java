package com.lab.hosaily.core.app.webservice;

import com.lab.hosaily.core.app.entity.News;
import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.app.service.NewsService;
import com.lab.hosaily.core.app.service.ProfileService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.UsingState;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Administrator on 2017/9/13.
 */
@RestController
@RequestMapping(value = "/api/1.0/app/news")
@Api(description = "客户动态资料")
public class NewsRestController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private ProfileService profileService;

    //    http://localhost:8080/swagger-ui.html
    private static final Logger logger = LoggerFactory.getLogger(NewsRestController.class);

    @ApiIgnore
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<News> save() {
        try {
            News news = new News();
            news.setOrganizationId("setOrganizationId");
            news.setComment("setComment");
            news.setPicUrls("setPicUrls");
            news.setProfileId("setProfileId");
            newsService.save(news);
            return new Response<News>("添加成功", news);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiIgnore
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<News> update() {
        try {
            News news = newsService.getById("4a60f582212442f68832a8dbe6ec0b5b");
            news.setOrganizationId("setOrganizationId1");
            news.setComment("setComment1");
            news.setPicUrls("setPicUrls1");
            news.setProfileId("setProfileId1");
            newsService.update(news);

            return new Response<News>("修改成功", news);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<News> getById(@PathVariable("id") String id) {
        try {
            News news = newsService.getById(id);
            return new Response<News>("查询成功", news);
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
    public Response<Page<News>> page(Long pageNum, Long pageSize, String profileId) {
        try {
            System.out.println("啊实打实大师大师大师大所多撒");
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<News> page = newsService.page(pageRequest, profileId);

            return new Response<Page<News>>("查询成功", page);
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
    public Response<String> upload(String picUrls, String comment, String uploaderId, String profileId) {
        try {
            Response<String> response = new Response<String>();


            if(StringUtils.isBlank(uploaderId)){
                response.setSuccess(false);
                response.setMsg("uploaderId不能为空！");
                return response;
            }
            if(StringUtils.isBlank(profileId)){
                response.setSuccess(false);
                response.setMsg("profileId不能为空！");
                return response;
            }


            News news = new News();
            news.setPicUrls(picUrls);
            news.setProfileId(profileId);
            news.setComment(comment);
            Profile profile = profileService.getById(uploaderId);
            System.out.println("aaaaaaaaaaaaaaa: " + profile.getName());
            news.setUploader(profile);
            newsService.save(news);
            response.setSuccess(true);
            response.setMsg("提交成功");
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
