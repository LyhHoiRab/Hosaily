package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.commons.utils.ProjectStatus;
import com.lab.hosaily.core.course.dao.mapper.AccountProjectMapper;
import com.lab.hosaily.core.course.entity.AccountProject;
import com.lab.hosaily.core.course.entity.Project;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AccountProjectDao {

    private static Logger logger = LoggerFactory.getLogger(AccountProjectDao.class);

    @Autowired
    private AccountProjectMapper mapper;

    @Autowired
    private ProjectDao projectDao;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(AccountProject accountProject) {
        try {
            if (null == accountProject.getCreateTime()) {
//                Assert.hasText(accountProject.getOrganizationId(), "企业ID不能为空");
                accountProject.setCreateTime(new Date());
                mapper.save(accountProject);
            } else {
                System.out.println("saveOrUpdate: " + accountProject.getStatus());
                accountProject.setUpdateTime(new Date());
                mapper.update(accountProject);
                Project project = projectDao.getById(accountProject.getProjectId());
                project.setCompletedCount((Integer.parseInt(project.getCompletedCount()) + 1) + "");
                projectDao.saveOrUpdate(project);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询标签
     */
    public AccountProject getByAccountIdAndProjectId(String accountId, String projectId) {
        try {
            Assert.hasText(accountId, "accountId不能为空");
            Assert.hasText(projectId, "projectId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("accountId", accountId));
            criteria.and(Restrictions.eq("projectId", projectId));

            return mapper.getByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

}
