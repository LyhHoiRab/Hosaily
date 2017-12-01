package com.lab.hosaily.core.application.service;

import com.lab.hosaily.core.application.dao.WebResourceDao;
import com.lab.hosaily.core.application.entity.WebResource;
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
public class WebResourceServiceImpl implements WebResourceService{

    private static Logger logger = LoggerFactory.getLogger(WebResourceServiceImpl.class);

    @Autowired
    private WebResourceDao webResourceDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(WebResource resource){
        try{
            Assert.notNull(resource, "网站资源目录信息不能为空");
            Assert.hasText(resource.getDomain(), "网站资源目录域名不能为空");
            Assert.hasText(resource.getImgUrl(), "网站资源图片目录不能为空");
            Assert.hasText(resource.getCssUrl(), "网站资源样式目录不能为空");
            Assert.hasText(resource.getJsUrl(), "网站资源JS目录不能为空");
            Assert.hasText(resource.getMobileImgUrl(), "网站资源移动端图片目录不能为空");
            Assert.hasText(resource.getMobileCssUrl(), "网站资源移动端样式目录不能为空");
            Assert.hasText(resource.getMobileJsUrl(), "网站资源移动端JS目录不能为空");

            webResourceDao.saveOrUpdate(resource);
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
    public void update(WebResource resource){
        try{
            Assert.notNull(resource, "网站资源目录信息不能为空");
            Assert.hasText(resource.getId(), "网站资源目录ID不能为空");

            webResourceDao.saveOrUpdate(resource);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "网站资源目录ID不能为空");

            WebResource resource = webResourceDao.getById(id);
            //删除记录
            webResourceDao.delete(id);
            //删除缓存
            webResourceDao.deleteCacheByDomain(resource.getDomain());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据域名查询缓存
     */
    @Override
    public WebResource getCacheByDomain(String domain){
        try{
            Assert.hasText(domain, "网站资源域名不能为空");

            return webResourceDao.getCacheByDomain(domain);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新缓存
     */
    @Override
    public void updateCache(){
        try{
            webResourceDao.updateCache();
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public WebResource getById(String id){
        try{
            Assert.hasText(id, "网站资源目录ID不能为空");

            return webResourceDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @Override
    public Page<WebResource> page(PageRequest pageRequest, UsingState state, String domain, String imgUrl, String cssUrl, String jsUrl, String organizationId, String organizationToken){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return webResourceDao.page(pageRequest, state, domain, imgUrl, cssUrl, jsUrl, organizationId, organizationToken);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    @Override
    public List<WebResource> list(UsingState state, String domain, String imgUrl, String cssUrl, String jsUrl, String organizationId, String organizationToken){
        try{
            return webResourceDao.list(state, domain, imgUrl, cssUrl, jsUrl, organizationId, organizationToken);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
