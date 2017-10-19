package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.consts.CourseKind;
import com.lab.hosaily.core.course.consts.CourseType;
import com.lab.hosaily.core.course.dao.mapper.CourseMapper;
import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.commons.security.exception.ApplicationException;
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

                if(CourseType.CHAPTER.equals(course.getType()) || CourseType.SECTION.equals(course.getType())){
                    Assert.hasText(course.getParentId(), "课程父级ID不能为空");
                }

                course.setId(UUIDGenerator.by32());
                course.setState(UsingState.NORMAL);
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
     * 分页查询课程记录
     */
    public Page<Course> pageByCourse(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            List<Course> list = mapper.findCourseByParams(criteria);
            Long count = mapper.countCourseByParams(criteria);

            return new Page<Course>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询帖子记录
     */
    public Page<Course> pageByPost(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.POST.getId()));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            List<Course> list = mapper.findPostByParams(criteria);
            Long count = mapper.countPostByParams(criteria);

            return new Page<Course>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询帖子
     */
    public Course getPostById(String id){
        try{
            Assert.hasText(id, "帖子ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.POST.getId()));
            criteria.and(Restrictions.eq("c.id", id));

            return mapper.getPostByParams(criteria);
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
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.and(Restrictions.eq("c.id", id));

            return mapper.getCourseByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询章节
     */
    public Course getChapterById(String id){
        try{
            Assert.hasText(id, "章节ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CHAPTER.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.and(Restrictions.eq("c.id", id));

            return mapper.getChapterByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询课时
     */
    public Course getSectionById(String id){
        try{
            Assert.hasText(id, "课时ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.SECTION.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.COURSE.getId()));
            criteria.and(Restrictions.eq("c.id", id));

            return mapper.getSectionByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
