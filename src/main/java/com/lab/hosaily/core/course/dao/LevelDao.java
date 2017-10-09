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
    public Page<Level> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("isDelete", false));
            criteria.setLimit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long total = mapper.countByParams(criteria);
            List<Level> list = mapper.findByParams(criteria);

            return new Page<Level>(list, pageRequest, total);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 懒加载查询
     */
    public List<Level> findLazyLoadingByState(UsingState state){
        try{
            Assert.notNull(state, "等级状态不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("l.state", state.getId()));
            criteria.and(Restrictions.eq("p.state", state.getId()));

            return mapper.findLazyLoadingByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
