package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.CourseGroupMapper;
import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.entity.CourseGroup;
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
public class CourseGroupDao{

    private static Logger logger = LoggerFactory.getLogger(CourseGroupDao.class);

    @Autowired
    private CourseGroupMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(CourseGroup group){
        try{
            Assert.notNull(group, "课程分组信息不能为空");

            if(StringUtils.isBlank(group.getId())){
                group.setId(UUIDGenerator.by32());
                group.setCreateTime(new Date());
                mapper.save(group);
            }else{
                group.setUpdateTime(new Date());
                mapper.update(group);
            }

            //删除课程
            mapper.deleteCourse(group.getId());

            //更新课程
            if(group.getCourse() != null && !group.getCourse().isEmpty()){
                mapper.addCourse(group.getId(), group.getCourse());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    public void delete(String id){
        try{
            Assert.hasText(id, "课程组ID不能为空");

            //删除课程组
            mapper.delete(id);
            //删除课程关联
            mapper.deleteCourse(id);
            //删除账户关联
            mapper.deleteAccountByGroupId(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 用户授权
     */
    public void authorization(String accountId, List<CourseGroup> groups){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            //删除账户
            mapper.deleteAccount(accountId);

            //更新账户
            if(groups != null && !groups.isEmpty()){
                mapper.addAccount(accountId, groups);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    public CourseGroup getById(String id){
        try{
            Assert.hasText(id, "课程组ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("g.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    public List<CourseGroup> list(String accountId, String groupId, String organizationId, String organizationToken, UsingState state, String name){
        try{
            Criteria criteria = new Criteria();

            if(!StringUtils.isBlank(groupId)){
                criteria.and(Restrictions.eq("g.id", groupId));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("g.organizationId", organizationId));
            }
            if(!StringUtils.isBlank(organizationToken)){
                criteria.and(Restrictions.eq("o.token", organizationToken));
            }
            if(state != null){
                criteria.and(Restrictions.eq("g.state", state.getId()));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("g.name", name));
            }
            if(!StringUtils.isBlank(accountId)){
                criteria.and(Restrictions.eq("ag.accountId", accountId));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    public Page<CourseGroup> page(PageRequest pageRequest, String groupId, String organizationId, String organizationToken, UsingState state, String name){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.groupBy(Restrictions.groupBy("g.id"));
            criteria.sort(Restrictions.asc("g.createTime"));

            if(!StringUtils.isBlank(groupId)){
                criteria.and(Restrictions.eq("g.id", groupId));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("g.organizationId", organizationId));
            }
            if(!StringUtils.isBlank(organizationToken)){
                criteria.and(Restrictions.eq("o.token", organizationToken));
            }
            if(state != null){
                criteria.and(Restrictions.eq("g.state", state.getId()));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("g.name", name));
            }

            List<String> ids = mapper.findIdByParams(criteria);
            Long count = mapper.countByParams(criteria);

            List<CourseGroup> list = new ArrayList<CourseGroup>();

            if(ids != null && !ids.isEmpty()){
                criteria.clear();
                criteria.and(Restrictions.in("g.id", ids));
                criteria.sort(Restrictions.asc("g.createTime"));

                list = mapper.findByParams(criteria);
            }

            return new Page(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 是否有授权课程
     */
    public Boolean hasCourse(String accountId, String courseId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");
            Assert.hasText(courseId, "课程ID不能为空");

            //旧数据中过度
            return mapper.hasCourse(accountId, courseId) || mapper.accountCourse(accountId, courseId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}

