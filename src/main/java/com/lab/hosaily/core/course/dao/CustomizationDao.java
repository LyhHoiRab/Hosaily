package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.CustomizationMapper;
import com.lab.hosaily.core.course.entity.Customization;
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
public class CustomizationDao{

    private static Logger logger = LoggerFactory.getLogger(CustomizationDao.class);

    @Autowired
    private CustomizationMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Customization customization){
        try{
            Assert.notNull(customization, "定制服务信息不能为空");

            if(StringUtils.isBlank(customization.getId())){
                customization.setId(UUIDGenerator.by32());
                customization.setCreateTime(new Date());
                mapper.save(customization);
            }else{
                customization.setUpdateTime(new Date());
                mapper.update(customization);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Customization getById(String id){
        try{
            Assert.hasText(id, "定制服务ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Customization> page(PageRequest pageRequest, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("sort"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

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
            List<Customization> list = mapper.findByParams(criteria);

            return new Page<Customization>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
