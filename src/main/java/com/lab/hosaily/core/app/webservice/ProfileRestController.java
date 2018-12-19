package com.lab.hosaily.core.app.webservice;

import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.app.service.ProfileService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.Sex;
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

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
@RestController
@RequestMapping(value = "/api/1.0/app/profile")
@Api(description = "客户")
public class ProfileRestController {

    @Autowired
    private ProfileService profileService;

    //    http://localhost:8080/swagger-ui.html
    private static final Logger logger = LoggerFactory.getLogger(ProfileRestController.class);

//    @ApiIgnore
    @ApiOperation(value = "新建爱情档案", notes = "新建爱情档案")
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Profile> save(@RequestBody Profile profile) {
        try {
            profileService.save(profile);
            return new Response<Profile>("添加成功", profile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    //    @ApiIgnore
    @ApiOperation(value = "新建爱情档案", notes = "新建爱情档案")
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Profile> edit(@RequestBody Profile profile) {
        try {
            profileService.update(profile);
            return new Response<Profile>("添加成功", profile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

//    @ApiIgnore
    @ApiOperation(value = "更新爱情档案", notes = "更新爱情档案")
    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Profile> update(String id, String name, Sex sex, String mobile, Integer age, String comment, String headImgUrl, String job, String address, String marryAge, String requirement,
                                    String problem, String nickname, String reason, String fixed, String originalFamily, String unforgettable, String selfAssessment, String otherAssessment, String sellerId) {
        try {
            Profile profile = new Profile();
            profile.setId(id);
            profile.setName(name);
            profile.setSex(sex);
            profile.setMobile(mobile);
            profile.setAge(age);
            profile.setComment(comment);
            profile.setHeadImgUrl(headImgUrl);
            profile.setJob(job);
            profile.setAddress(address);
            profile.setMarryAge(marryAge);
            profile.setRequirement(requirement);
            profile.setProblem(problem);
            profile.setNickname(nickname);
            profile.setReason(reason);
            profile.setFixed(fixed);
            profile.setOriginalFamily(originalFamily);
            profile.setUnforgettable(unforgettable);
            profile.setSelfAssessment(selfAssessment);
            profile.setOtherAssessment(otherAssessment);
            profile.setSellerId(sellerId);

            profileService.update(profile);
            return new Response<Profile>("修改成功", profile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "爱情档案", notes = "爱情档案")
    @ApiImplicitParam(name = "id", value = "客户ID", paramType = "path", required = true, dataType = "String")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Profile> getById(@PathVariable("id") String id) {
        try {
            Profile profile = profileService.getById(id);
            return new Response<Profile>("查询成功", profile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    @ApiOperation(value = "我的详情", notes = "我的详情")
    @ApiImplicitParam(name = "accountId", value = "我的account ID", paramType = "query", required = true, dataType = "String")
    @RequestMapping(value = "/getByAccountId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Profile> getByAccountId(String accountId) {
        try {
            Profile profile = profileService.getByAccountId(accountId);
            return new Response<Profile>("查询成功", profile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "客户列表", notes = "客户列表分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "hasSignAgreement", value = "已签合同0(未)/1(已)", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "hasSignProfile", value = "已签爱情档案0(未)/1(已)", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "hasUploaded", value = "已上传资料0(未)/1(已)", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "name", value = "客户名称", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "sellerId", value = "销售ID/咨询师ID（销售端和资讯端必填，其他不填）", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Profile>> page(Long pageNum, Long pageSize, Integer hasSignAgreement, Integer hasSignProfile, Integer hasUploaded, String name, String sellerId) {
        try {
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Profile> page = profileService.page(pageRequest, hasSignAgreement, hasSignProfile, hasUploaded, name, sellerId);

            return new Response<Page<Profile>>("查询成功", page);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }



    @ApiOperation(value = "导师列表", notes = "导师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advisorName", value = "导师名", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "role", value = "档案类型", paramType = "query", required = false, dataType = "Integer")
    })
    @RequestMapping(value = "/advisorList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Profile>> advisorList(String advisorName, Integer role) {
        try {
            List<Profile> profiles = profileService.list(advisorName, role);
            return new Response<List<Profile>>("查询成功", profiles);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }





    @ApiOperation(value = "资讯端获取客户列表", notes = "资讯端获取客户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientName", value = "客户名称", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "页码", paramType = "query", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", paramType = "query", required = true, dataType = "Long")
    })
    @RequestMapping(value = "/findClientsPage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Profile>> findClientsPage(String clientName, String advisorId, Long pageNum, Long pageSize) {
        try {
            Response<Page<Profile>>  response = new Response<Page<Profile>>();
//            if(StringUtils.isBlank(clientName)){
//                response.setSuccess(false);
//                response.setMsg("clientName不能为空");
//                return response;
//            }
            if(StringUtils.isBlank(advisorId)){
                response.setSuccess(false);
                response.setMsg("advisorId");
                return response;
            }
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Profile> page = profileService.findClientsPage(pageRequest, clientName, advisorId);
            response.setResult(page);
            return response;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
