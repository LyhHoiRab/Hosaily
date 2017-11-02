package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.LevelMapper;
import com.lab.hosaily.core.course.entity.Level;
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
public class LevelDao{

    private static Logger logger = LoggerFactory.getLogger(LevelDao.class);

    @Autowired
    private LevelMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Level level){
        try{
            Assert.notNull(level, "等级信息不能为空");

            if(StringUtils.isBlank(level.getId())){
                level.setId(UUIDGenerator.by32());
                level.setState(UsingState.NORMAL);
                level.setIsDelete(false);
                level.setCreateTime(new Date());
                mapper.save(level);
            }else{
                level.setUpdateTime(new Date());
                mapper.update(level);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    public Level getById(String id){
        try{
            Assert.hasText(id, "等级ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("l.id", id));
            criteria.and(Restrictions.eq("l.isDelete", false));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据状态查询记录
     */
    public List<Level> findByState(UsingState state){
        try{
            Assert.notNull(state, "等级状态不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("l.state", state.getId()));
            criteria.and(Restrictions.eq("l.isDelete", false));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Level> list(UsingState state){
        try{
            Criteria criteria = new Criteria();

            if(state != null){
                criteria.and(Restrictions.eq("l.state", state.getId()));
            }

            criteria.and(Restrictions.eq("l.isDelete", false));
            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Level> page(PageRequest pageRequest, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("l.isDelete", false));
            criteria.groupBy(Restrictions.groupBy("l.id"));
            criteria.sort(Restrictions.desc("p.price"));
            criteria.setLimit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(state != null){
                criteria.and(Restrictions.eq("l.state", state.getId()));
            }
            if(createTime != null){
                criteria.and(Restrictions.eq("l.createTime", createTime));
            }
            if(minCreateTime != null && maxCreateTime != null){
                criteria.and(Restrictions.between("l.createTime", minCreateTime, maxCreateTime));
            }

            //分页查询ID
            List<String> ids = mapper.findIdByParams(criteria);
            //查询记录数
            Long count = mapper.countByParams(criteria);

            List<Level> list = new ArrayList<Level>();

            if(!ids.isEmpty()){
                criteria.clear();
                criteria.sort(Restrictions.desc("p.price"));

                list.addAll(mapper.findByParams(criteria));
            }

            return new Page<Level>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
