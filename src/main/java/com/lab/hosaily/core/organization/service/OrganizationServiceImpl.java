package com.lab.hosaily.core.organization.service;

import com.lab.hosaily.core.organization.dao.OrganizationDao;
import com.lab.hosaily.core.organization.entity.Organization;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrganizationServiceImpl implements OrganizationService{

    private static Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    private OrganizationDao organizationDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Organization organization){
        try{
            Assert.notNull(organization, "组织信息不能为空");

            organizationDao.saveOrUpdate(organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Organization organization){
        try{
            Assert.notNull(organization, "组织信息不能为空");
            Assert.hasText(organization.getId(), " 组织ID不能为空");

            organizationDao.saveOrUpdate(organization);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Organization getById(String id){
        try{
            Assert.hasText(id, "组织ID不能为空");

            return organizationDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @Override
    public Page<Organization> page(PageRequest pageRequest, String name, String token, UsingState state){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return organizationDao.page(pageRequest, name, token, state);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    @Override
    public List<Organization> list(String name, String token, UsingState state){
        try{
            return organizationDao.list(name, token, state);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
