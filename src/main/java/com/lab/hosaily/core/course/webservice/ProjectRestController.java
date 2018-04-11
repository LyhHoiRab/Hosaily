package com.lab.hosaily.core.course.webservice;

import com.lab.hosaily.commons.utils.ProjectStatus;
import com.lab.hosaily.core.course.entity.AccountProject;
import com.lab.hosaily.core.course.entity.Project;
import com.lab.hosaily.core.course.service.ProjectService;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/api/1.0/project")
public class ProjectRestController {

    private static Logger logger = LoggerFactory.getLogger(ProjectRestController.class);

    @Autowired
    private ProjectService projectService;

    /**
     * 保存记录
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Project> save(@RequestBody Project project){
        try{
            projectService.save(project);
            return new Response<Project>("添加成功", project);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Project> update(@RequestBody Project project){
        try{
            projectService.update(project);
            return new Response<Project>("修改成功", project);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Project> getById(@PathVariable("id") String id){
        try{
            Project project = projectService.getById(id);
            return new Response<Project>("查询成功", project);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Project>> page(Long pageNum, Long pageSize, String num, String title, String organizationId, String status, String accountId){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
//            session or param
//            String accountId = "";
            System.out.println("RequestMappingRequestMappingRequestMappingRequestMappingRequestMappingRequestMappingRequestMappingRequestMappingRequestMapping");
            Page<Project> page = projectService.page(pageRequest, num, title, organizationId, status, accountId);

            return new Response<Page<Project>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    /**
     * 分页查询
     */
    @RequestMapping(value = "/summitResult", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> summitResult(String accountId, String projectId, String questionId){
        try{
            AccountProject accountProject = new AccountProject();
            accountProject.setAccountId(accountId);
            accountProject.setProjectId(projectId);
            accountProject.setResultId(questionId);
            accountProject.setStatus(ProjectStatus.PROJECT_DONE);
            projectService.summitResult(accountProject);
            return new Response<String>("提交成功", "");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }



    /**
     * 分页查询
     */
    @RequestMapping(value = "/shareProject" +
            "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> shareProject(String accountId, String projectId){
        try{
            AccountProject accountProject = new AccountProject();
            accountProject.setAccountId(accountId);
            accountProject.setProjectId(projectId);
//            accountProject.setResultId(questionId);
            accountProject.setStatus(ProjectStatus.PROJECT_UNDONE);
            projectService.summitResult(accountProject);
            return new Response<String>("分享成功", "");
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
            projectService.delete(id);

            return new Response("删除成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }


    /**
     * 查询列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<Project>> list(String nickname, String name, UsingState state, String organizationId, String organizationToken){
        try{
            List<Project> list = projectService.list(nickname, name, state, organizationId, organizationToken);

            return new Response<List<Project>>("查询成功", list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<String> upload(@RequestParam("file") CommonsMultipartFile file){
        try{
            String url = projectService.upload(file);
            return new Response<String>("上传成功", url);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}