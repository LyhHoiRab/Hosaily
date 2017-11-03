package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.consts.CourseKind;
import com.lab.hosaily.core.course.consts.CourseType;
import com.lab.hosaily.core.course.dao.mapper.CourseMapper;
import com.lab.hosaily.core.course.entity.Course;
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
public class CourseDao{

    private static Logger logger = LoggerFactory.getLogger(CourseDao.class);

    @Autowired
    private CourseMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Course course){
        try{
            Assert.notNull(course, "课程信息不能为空");

            if(StringUtils.isBlank(course.getId())){
                Assert.notNull(course.getType(), "课程层级类型不能为空");
                Assert.notNull(course.getKind(), "课程类型不能为空");

                course.setId(UUIDGenerator.by32());
                course.setCreateTime(new Date());
                mapper.save(course);
            }else{
                course.setUpdateTime(new Date());
                mapper.update(course);
            }

            //更新等级
            if(course.getLevel() != null && !course.getLevel().isEmpty()){
                mapper.deleteLevel(course.getId());
                mapper.addLevel(course.getId(), course.getLevel());
            }
            //更新标签
            if(course.getTag() != null && !course.getTag().isEmpty()){
                mapper.deleteTag(course.getId());
                mapper.addTag(course.getId(), course.getTag());
            }
            //更新媒体
            if(course.getMedia() != null && !course.getMedia().isEmpty()){
                mapper.deleteMedia(course.getId());
                mapper.addMedia(course.getId(), course.getMedia());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询课程
     */
    public Course getCourseById(String id){
        try{
            Assert.hasText(id, "课程ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.id", id));
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.sort(Restrictions.asc("ch.sort"));

            return mapper.getCourseByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询章节
     */
    public Course getChapterById(String id){
        try{
            Assert.hasText(id, "章节ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.id", id));
            criteria.and(Restrictions.eq("c.type", CourseType.CHAPTER.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.sort(Restrictions.asc("ch.sort"));

            return mapper.getChapterByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询课时
     */
    public Course getSectionById(String id){
        try{
            Assert.hasText(id, "课时ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.id", id));
            criteria.and(Restrictions.eq("c.type", CourseType.SECTION.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));

            return mapper.getSectionByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据状态查询课程
     */
    public List<Course> findCourseByState(UsingState state){
        try{
            Assert.notNull(state, "课程状态不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.and(Restrictions.eq("c.state", state.getId()));

            return mapper.findCourseByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询课程列表
     */
    public List<Course> listByCourse(UsingState state){
        try{
            Assert.notNull(state, "课程状态不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));

            if(state != null){
                criteria.and(Restrictions.eq("c.state", state.getId()));
            }

            return mapper.findCourseByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询课程
     */
    public Page<Course> pageByCourse(PageRequest pageRequest, String tagName, String advisor, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime, String accountId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.groupBy(Restrictions.groupBy("c.id"));
            criteria.sort(Restrictions.asc("c.sort"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(tagName)){
                criteria.and(Restrictions.like("t.name", tagName));
            }
            if(!StringUtils.isBlank(advisor)){
                criteria.and(Restrictions.like("a.name", advisor));
            }
            if(state != null){
                criteria.and(Restrictions.eq("c.state", state.getId()));
            }
            if(createTime != null){
                criteria.and(Restrictions.eq("c.createTime", createTime));
            }
            if(minCreateTime != null && maxCreateTime != null){
                criteria.and(Restrictions.between("c.createTime", minCreateTime, maxCreateTime));
            }
            if(!StringUtils.isBlank(accountId)){
                criteria.and(Restrictions.eq("ac.accountId", accountId));
            }

            //分页查询ID
            List<String> ids = mapper.findIdByParams(criteria);
            //查询记录数
            Long count = mapper.countByParams(criteria);

            List<Course> list = new ArrayList<Course>();

            if(!ids.isEmpty()){
                criteria.clear();
                criteria.and(Restrictions.in("c.id", ids));
                criteria.sort(Restrictions.asc("c.sort"));

                list.addAll(mapper.findCourseByParams(criteria));
            }

            return new Page<Course>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询章节
     */
    public Page<Course> pageByChapter(PageRequest pageRequest, String parentId, UsingState state){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CHAPTER.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.groupBy(Restrictions.groupBy("c.id"));
            criteria.sort(Restrictions.asc("c.sort"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(parentId)){
                criteria.and(Restrictions.eq("c.parentId", parentId));
            }
            if(state != null){
                criteria.and(Restrictions.eq("c.state", state.getId()));
            }

            //分页查询ID
            List<String> ids = mapper.findIdByParams(criteria);
            //查询记录数
            Long count = mapper.countByParams(criteria);

            List<Course> list = new ArrayList<Course>();

            if(!ids.isEmpty()){
                criteria.clear();
                criteria.and(Restrictions.in("c.id", ids));
                criteria.sort(Restrictions.asc("c.sort"));
                criteria.sort(Restrictions.asc("ch.sort"));

                list.addAll(mapper.findChapterByParams(criteria));
            }

            return new Page<Course>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
