package com.lab.hosaily.core.organization.dao;

import com.lab.hosaily.core.organization.dao.mapper.OrganizationMapper;
import com.lab.hosaily.core.organization.entity.Organization;
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
public class OrganizationDao{

    private static Logger logger = LoggerFactory.getLogger(OrganizationDao.class);

    @Autowired
    private OrganizationMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Organization organization){
        try{
            Assert.notNull(organization, "组织信息不能为空");

            if(StringUtils.isBlank(organization.getId())){
                Assert.hasText(organization.getToken(), "组织Token不能为空");

                organization.setId(UUIDGenerator.by32());
                organization.setCreateTime(new Date());
                mapper.save(organization);
            }else{
                organization.setUpdateTime(new Date());
                mapper.update(organization);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Organization> page(PageRequest pageRequest, String name, String token, UsingState state){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(!StringUtils.isBlank(token)){
                criteria.and(Restrictions.like("token", token));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }

            List<Organization> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Organization>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Organization getById(String id){
        try{
            Assert.hasText(id, "组织ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    public List<Organization> list(String name, String token, UsingState state){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));

            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(!StringUtils.isBlank(token)){
                criteria.and(Restrictions.like("token", token));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
