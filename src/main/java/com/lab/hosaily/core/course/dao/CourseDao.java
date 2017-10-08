package com.lab.hosaily.core.course.dao;

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
                Assert.notNull(course.getType(), "课程类型不能为空");

                Double price = course.getPrice() == null || course.getPrice() < 0 ? 0 : course.getPrice();
                Integer likes = course.getLikes() == null || course.getLikes() < 0 ? 0 : course.getLikes();
                Integer view = course.getView() == null || course.getView() < 0 ? 0 : course.getView();
                Integer weight = course.getWeight() == null || course.getWeight() < 0 ? 0 : course.getWeight();

                course.setId(UUIDGenerator.by32());
                course.setPrice(price);
                course.setLikes(likes);
                course.setView(view);
                course.setWeight(weight);
                course.setState(UsingState.NORMAL);
                course.setCreateTime(new Date());
                mapper.save(course);
                //关联表维护
                if(course.getMedia() != null && !course.getMedia().isEmpty()){
                    mapper.addMedia(course.getId(), course.getMedia());
                }
                if(course.getLevel() != null && !course.getLevel().isEmpty()){
                    mapper.addLevel(course.getId(), course.getLevel());
                }
                if(course.getTag() != null && !course.getTag().isEmpty()){
                    mapper.addTag(course.getId(), course.getTag());
                }
            }else{
                course.setUpdateTime(new Date());
                mapper.update(course);
                //关联表维护
                if(course.getMedia() != null && !course.getMedia().isEmpty()){
                    mapper.deleteMedia(course.getId());
                    mapper.addMedia(course.getId(), course.getMedia());
                }
                if(course.getLevel() != null && !course.getLevel().isEmpty()){
                    mapper.deleteLevel(course.getId());
                    mapper.addLevel(course.getId(), course.getLevel());
                }
                if(course.getTag() != null && !course.getTag().isEmpty()){
                    mapper.deleteTag(course.getId());
                    mapper.addTag(course.getId(), course.getTag());
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Course> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("type", CourseType.CATALOGUE.getId()));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<Course> list = mapper.findByParams(criteria);

            return new Page<Course>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
