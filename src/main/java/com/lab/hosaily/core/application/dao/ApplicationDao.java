package com.lab.hosaily.core.application.dao;

import com.lab.hosaily.core.application.consts.ApplicationType;
import com.lab.hosaily.core.application.dao.mapper.ApplicationMapper;
import com.lab.hosaily.core.application.entity.Application;
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
public class ApplicationDao{

    private static Logger logger = LoggerFactory.getLogger(ApplicationDao.class);

    @Autowired
    private ApplicationMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Application application){
        try{
            Assert.notNull(application, "应用信息不能为空");

            if(StringUtils.isBlank(application.getId())){
                Assert.hasText(application.getAppId(), "应用AppId不能为空");
                Assert.hasText(application.getToken(), "应用Token不能为空");
                Assert.notNull(application.getType(), "应用类型不能为空");

                application.setId(UUIDGenerator.by32());
                application.setIsDelete(false);
                application.setCreateTime(new Date());
                mapper.save(application);
            }else{
                application.setUpdateTime(new Date());
                mapper.update(application);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Application getById(String id){
        try{
            Assert.hasText(id, "应用ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.id", id));
            criteria.and(Restrictions.eq("a.isDelete", false));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据Token查询
     */
    public Application getByToken(String token){
        try{
            Assert.hasText(token, "应用Token不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.token", token));
            criteria.and(Restrictions.eq("a.isDelete", false));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Application> page(PageRequest pageRequest, UsingState state, String name, String token, ApplicationType type, String organizationId, String organizationToken){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.isDelete", false));
            criteria.setLimit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("a.name", name));
            }
            if(!StringUtils.isBlank(token)){
                criteria.and(Restrictions.like("a.token", token));
            }
            if(type != null){
                criteria.and(Restrictions.eq("a.type", type.getId()));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("a.organizationId", organizationId));
            }
            if(!StringUtils.isBlank(organizationToken)){
                criteria.and(Restrictions.eq("o.token", organizationToken));
            }
            if(state != null){
                criteria.and(Restrictions.eq("a.state", state.getId()));
            }

            Long count = mapper.countByParams(criteria);
            List<Application> list = mapper.findByParams(criteria);

            return new Page<Application>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
