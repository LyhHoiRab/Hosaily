package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.commons.utils.ProjectStatus;
import com.lab.hosaily.core.course.dao.mapper.ProjectMapper;
import com.lab.hosaily.core.course.entity.Advisor;
import com.lab.hosaily.core.course.entity.Project;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ProjectDao {

    private static Logger logger = LoggerFactory.getLogger(ProjectDao.class);

    @Autowired
    private ProjectMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Project project) {
        try {
            if (StringUtils.isBlank(project.getId())) {
                Assert.hasText(project.getOrganizationId(), "企业ID不能为空");
                project.setId(UUIDGenerator.by32());
                project.setCreateTime(new Date());
                mapper.save(project);
            } else {
                project.setUpdateTime(new Date());
                mapper.update(project);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除标签
     */
    public void delete(String id) {
        try {
            Assert.hasText(id, "标签id不能为空");

            mapper.delete(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询标签
     */
    public Project getById(String id) {
        try {
            Assert.hasText(id, "标签id不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("p.id", id));

            return mapper.getByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Project> list(UsingState state) {
        try {
            Criteria criteria = new Criteria();
            return mapper.findByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Project> page(PageRequest pageRequest, String num, String title, String organizationId, String status, String accountId) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");
            System.out.println("DaoDaoDaoDaoDaoDaoDaoDaoDaoDaoDaoDaoDaoDaoDao: " + status);
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("p.order"));
            if (!StringUtils.isBlank(num)) {
                criteria.and(Restrictions.like("p.num", num));
            }
            if (!StringUtils.isBlank(title)) {
                criteria.and(Restrictions.like("p.title", title));
            }
            if (!StringUtils.isBlank(organizationId)) {
                criteria.and(Restrictions.like("p.organizationId", organizationId));
            }
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            Long count = 0l;
            List<Project> list = new ArrayList<Project>();
            if (ProjectStatus.PROJECT_DONE.equals(status)) {
                criteria.and(Restrictions.like("ap.status", ProjectStatus.PROJECT_DONE));
                criteria.and(Restrictions.like("ap.accountId", accountId));
                criteria.and(Restrictions.eq("ap.state", UsingState.NORMAL));
                count = mapper.countByParams(criteria);
                list = mapper.findByParams(criteria);
            } else if (ProjectStatus.PROJECT_UNDONE.equals(status)) {
                criteria.and(Restrictions.like("ap.status", ProjectStatus.PROJECT_UNDONE));
                criteria.and(Restrictions.like("ap.accountId", accountId));
                criteria.and(Restrictions.eq("ap.state", UsingState.NORMAL));
                count = mapper.countByParams(criteria);
                list = mapper.findByParams(criteria);
            } else if(ProjectStatus.PROJECT_ALL.equals(status)){
//                    查找所有测试项目，包含购买信息和测试结果
                System.out.println("查找所有测试项目，包含购买信息和测试结果");
                if (!StringUtils.isBlank(accountId)) {
//                    count = mapper.countByParams(criteria);
//                    list = mapper.findByParamsByPage(accountId, pageRequest.getOffset(), pageRequest.getPageSize());
//                    criteria.and(Restrictions.like("ap.status", ProjectStatus.PROJECT_UNDONE));
                    criteria.and(Restrictions.like("ap.accountId", accountId));
                    criteria.and(Restrictions.eq("ap.state", UsingState.NORMAL));
                    count = mapper.countByParams(criteria);
                    list = mapper.findByParams(criteria);
                }
            }else{
                System.out.println("查找所有测试项目，包含购买信息和测试结果22222");
                count = mapper.countByParams(criteria);
                list = mapper.findByParams(criteria);
            }
            return new Page<Project>(list, pageRequest, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    /**
     * 查询列表
     */
    public List<Project> list(String nickname, String name, UsingState state, String organizationId, String organizationToken) {
        try {
            Criteria criteria = new Criteria();
//            criteria.sort(Restrictions.asc("p.sort"));
//
//            if(!StringUtils.isBlank(nickname)){
//                criteria.and(Restrictions.like("a.nickname", nickname));
//            }
//            if(!StringUtils.isBlank(name)){
//                criteria.and(Restrictions.like("a.name", name));
//            }
//            if(state != null){
//                criteria.and(Restrictions.eq("a.state", state.getId()));
//            }
//            if(!StringUtils.isBlank(organizationId)){
//                criteria.and(Restrictions.eq("a.organizationId", organizationId));
//            }
//            if(!StringUtils.isBlank(organizationToken)){
//                criteria.and(Restrictions.eq("o.token", organizationToken));
//            }

            return mapper.findByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
