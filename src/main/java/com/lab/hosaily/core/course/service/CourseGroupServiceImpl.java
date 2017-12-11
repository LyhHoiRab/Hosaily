package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.dao.CourseDao;
import com.lab.hosaily.core.course.dao.CourseGroupDao;
import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.entity.CourseGroup;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CourseGroupServiceImpl implements CourseGroupService{

    private static Logger logger = LoggerFactory.getLogger(CourseGroupServiceImpl.class);

    @Autowired
    private CourseGroupDao courseGroupDao;

    @Autowired
    private CourseDao courseDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(CourseGroup group){
        try{
            Assert.notNull(group, "课程分组不能为空");

            courseGroupDao.saveOrUpdate(group);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(CourseGroup group){
        try{
            Assert.notNull(group, "课程分组不能为空");
            Assert.hasText(group.getId(), "课程分组ID不能为空");

            courseGroupDao.saveOrUpdate(group);
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
            Assert.hasText(id, "课程组ID不能为空");

            courseGroupDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 用户授权
     */
    @Override
    @Transactional(readOnly = false)
    public void authorization(String accountId, List<CourseGroup> groups){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            courseGroupDao.authorization(accountId, groups);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    @Override
    public List<CourseGroup> list(String accountId, String groupId, String organizationId, String organizationToken, UsingState state, String name){
        try{
            return courseGroupDao.list(accountId, groupId, organizationId, organizationToken, state, name);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<CourseGroup> page(PageRequest pageRequest, String groupId, String organizationId, String organizationToken, UsingState state, String name){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return courseGroupDao.page(pageRequest, groupId, organizationId, organizationToken, state, name);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public CourseGroup getById(String id){
        try{
            Assert.hasText(id, "课程分组ID不能为空");

            return courseGroupDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 是否有授权课程
     */
    public Boolean hasCourse(String accountId, String courseId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");
            Assert.hasText(courseId, "课程ID不能为空");

            Course course = courseDao.getCourseById(courseId);

            if(course.getAuthorization()){
                return true;
            }

            return courseGroupDao.hasCourse(accountId, courseId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
