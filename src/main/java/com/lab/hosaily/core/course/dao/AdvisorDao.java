package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.AdvisorMapper;
import com.lab.hosaily.core.course.entity.Advisor;
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
public class AdvisorDao{

    private static Logger logger = LoggerFactory.getLogger(AdvisorDao.class);

    @Autowired
    private AdvisorMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Advisor advisor){
        try{
            Assert.notNull(advisor, "导师信息不能为空");

            if(StringUtils.isBlank(advisor.getId())){
                Assert.notNull(advisor.getState(), "导师状态不能为空");

                advisor.setId(UUIDGenerator.by32());
                advisor.setCreateTime(new Date());
                mapper.save(advisor);
            }else{
                advisor.setUpdateTime(new Date());
                mapper.update(advisor);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Advisor getById(String id){
        try{
            Assert.hasText(id, "导师ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据状态查询
     */
    public List<Advisor> findByState(UsingState state){
        try{
            Assert.notNull(state, "导师状态不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("state", state.getId()));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Advisor> page(PageRequest pageRequest, String nickname, String name, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("sort"));

            if(!StringUtils.isBlank(nickname)){
                criteria.and(Restrictions.like("nickname", nickname));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }
            if(createTime != null){
                criteria.and(Restrictions.eq("createTime", createTime));
            }
            if(minCreateTime != null && maxCreateTime != null){
                criteria.and(Restrictions.between("createTime", minCreateTime, maxCreateTime));
            }

            Long count = mapper.countByParams(criteria);
            List<Advisor> list = mapper.findByParams(criteria);

            return new Page<Advisor>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Advisor> list(String nickname, String name, UsingState state, Date createTime){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("sort"));

            if(!StringUtils.isBlank(nickname)){
                criteria.and(Restrictions.like("nickname", nickname));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }
            if(createTime != null){
                criteria.and(Restrictions.eq("createTime", createTime));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
