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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;

@Repository
public class CourseDao{

    private static Logger logger = LoggerFactory.getLogger(CourseDao.class);

    @Autowired
    private CourseMapper mapper;

    /**
     * 根据条件查询记录
     */
    public List<Course> findByParams(Criteria criteria){
        try{
            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据条件查询记录数量
     */
    public Long countByParams(Criteria criteria){
        try{
            return mapper.countByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
