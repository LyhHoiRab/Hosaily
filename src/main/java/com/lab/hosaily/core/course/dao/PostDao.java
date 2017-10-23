package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.consts.CourseKind;
import com.lab.hosaily.core.course.consts.CourseType;
import com.lab.hosaily.core.course.dao.mapper.PostMapper;
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
public class PostDao{

    private static Logger logger = LoggerFactory.getLogger(PostDao.class);

    @Autowired
    private PostMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Course post){
        try{
            Assert.notNull(post, "帖子信息不能为空");

            if(StringUtils.isBlank(post.getId())){
                Assert.notNull(post.getState(), "帖子状态不能为空");

                post.setId(UUIDGenerator.by32());
                post.setCreateTime(new Date());
                mapper.save(post);
            }else{
                post.setUpdateTime(new Date());
                mapper.update(post);
            }

            //更新子课程
            if(post.getChildren() != null && !post.getChildren().isEmpty()){
                mapper.deleteCourse(post.getId());
                mapper.addCourse(post.getId(), post.getChildren());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Course getById(String id){
        try{
            Assert.hasText(id, "帖子ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Course> page(PageRequest pageRequest, String advisor, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.type", CourseType.CATALOGUE.getId()));
            criteria.and(Restrictions.eq("c.kind", CourseKind.POST.getId()));
            criteria.groupBy(Restrictions.groupBy("c.id"));
            criteria.sort(Restrictions.asc("c.sort"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

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

            //分页查询ID
            List<String> ids = mapper.findIdByParams(criteria);
            //查询记录数
            Long count = mapper.countByParams(criteria);

            List<Course> list = new ArrayList<Course>();

            if(!ids.isEmpty()){
                criteria.clear();
                criteria.and(Restrictions.in("c.id", ids));
                criteria.sort(Restrictions.asc("c.sort"));

                list.addAll(mapper.findByParams(criteria));
            }

            return new Page<Course>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
