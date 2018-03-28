package com.lab.hosaily.core.course.service;

import com.lab.hosaily.commons.utils.FileNameUtils;
import com.lab.hosaily.commons.utils.UpyunUtils;
import com.lab.hosaily.core.course.dao.AccountProjectDao;
import com.lab.hosaily.core.course.dao.ProjectDao;
import com.lab.hosaily.core.course.entity.AccountProject;
import com.lab.hosaily.core.course.entity.Project;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.FileUtils;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private AccountProjectDao accountProjectDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Project project){
        try{
            Assert.notNull(project, "标签信息不能为空");


//            测试
//            String id = UUIDGenerator.by32();
//            AccountProject accountProject = new AccountProject();
//            accountProject.setAccountId("aaa");
//            accountProject.setProjectId("ppp");
//            accountProject.setState(UsingState.INACTIVE);
//            accountProject.setStatus("U");
//            accountProjectDao.saveOrUpdate(accountProject);
//
//
//            AccountProject accountProject1 = accountProjectDao.getByAccountIdAndProjectId("0033dd5abb604729aef6bdc20d8221a5","fb97dbf4a63e48938f80958003bbd1cb");
//            accountProject1.setState(UsingState.NORMAL);
//            accountProject1.setStatus("D");
//            accountProjectDao.saveOrUpdate(accountProject1);

            projectDao.saveOrUpdate(project);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Project project){
        try{
            Assert.notNull(project, "标签信息不能为空");
            Assert.hasText(project.getId(), "标签sim不能为空");

            projectDao.saveOrUpdate(project);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "帖子ID不能为空");

            projectDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Project getById(String id){
        try{
            Assert.hasText(id, "标签信息不能为空");

            return projectDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Project> page(PageRequest pageRequest, String num, String title, String organizationId, String status, String accountId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");
            System.out.println("implimplimplimplimplimplimplimpl");
            return projectDao.page(pageRequest, num, title, organizationId, status, accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<Project> list(String nickname, String name, UsingState state, String organizationId, String organizationToken){
        try{
            return projectDao.list(nickname, name, state, organizationId, organizationToken);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 上传图片
     */
    @Override
    public String upload(CommonsMultipartFile file){
        try{
            Assert.notNull(file, "上传文件不能为空");

            //文件名称
            String originalFilename = file.getOriginalFilename();
            //MD5
            String md5 = FileUtils.getMD5(file.getBytes());
            //文件后缀
            String suffix = FileNameUtils.getSuffix(originalFilename);
            //上传名称
            String name = md5 + suffix;
            //上传路径
            String uploadPath = UpyunUtils.EMOTION_TEST_DIR + name;
            //上传
            boolean result = UpyunUtils.upload(uploadPath, file);

            return result ? UpyunUtils.URL + uploadPath : "";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void summitResult(AccountProject accountProject) {

        AccountProject oldAccountProject = accountProjectDao.getByAccountIdAndProjectId(accountProject.getAccountId(), accountProject.getProjectId());
        if(null != oldAccountProject){
            oldAccountProject.setResultId(accountProject.getResultId());
            oldAccountProject.setStatus(accountProject.getStatus());
        }
        accountProjectDao.saveOrUpdate(accountProject);
    }
}
