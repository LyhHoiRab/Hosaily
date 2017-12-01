package com.lab.hosaily.core.application.dao;

import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.core.application.dao.mapper.WebResourceMapper;
import com.lab.hosaily.core.application.entity.WebResource;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.RedisUtils;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import redis.clients.jedis.ShardedJedisPool;

import java.util.*;

@Repository
public class WebResourceDao{

    private static Logger logger = LoggerFactory.getLogger(WebResourceDao.class);

    @Autowired
    private WebResourceMapper mapper;

    @Autowired
    private ShardedJedisPool jedisPool;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(WebResource resource){
        try{
            Assert.notNull(resource, "网站资源目录信息不能为空");

            if(StringUtils.isBlank(resource.getId())){
                Assert.hasText(resource.getDomain(), "网站资源目录域名不能为空");
                Assert.hasText(resource.getImgUrl(), "网站资源图片目录不能为空");
                Assert.hasText(resource.getCssUrl(), "网站资源样式目录不能为空");
                Assert.hasText(resource.getJsUrl(), "网站资源JS目录不能为空");
                Assert.hasText(resource.getMobileImgUrl(), "网站资源移动端图片目录不能为空");
                Assert.hasText(resource.getMobileCssUrl(), "网站资源移动端样式目录不能为空");
                Assert.hasText(resource.getMobileJsUrl(), "网站资源移动端JS目录不能为空");

                resource.setId(UUIDGenerator.by32());
                resource.setCreateTime(new Date());
                mapper.save(resource);
            }else{
                resource.setUpdateTime(new Date());
                mapper.update(resource);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    public void delete(String id){
        try{
            Assert.hasText(id, "网站资源目录ID不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public WebResource getById(String id){
        try{
            Assert.hasText(id, "网站资源目录ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据域名查询
     */
    public WebResource getByDomain(String domain){
        try{
            Assert.hasText(domain, "网站资源目录域名不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.domain", domain));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 缓存
     */
    public void cache(WebResource resource){
        try{
            Assert.notNull(resource, "网站资源目录信息不能为空");
            Assert.hasText(resource.getDomain(), "网站资源目录域名不能为空");
            Assert.hasText(resource.getImgUrl(), "网站资源图片目录不能为空");
            Assert.hasText(resource.getCssUrl(), "网站资源样式目录不能为空");
            Assert.hasText(resource.getJsUrl(), "网站资源JS目录不能为空");
            Assert.hasText(resource.getMobileImgUrl(), "网站资源移动端图片目录不能为空");
            Assert.hasText(resource.getMobileCssUrl(), "网站资源移动端样式目录不能为空");
            Assert.hasText(resource.getMobileJsUrl(), "网站资源移动端JS目录不能为空");

            RedisUtils.hset(jedisPool.getResource(), RedisConsts.WEB_RESOURCE, resource.getDomain(), resource);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据域名查询缓存
     */
    public WebResource getCacheByDomain(String domain){
        try{
            Assert.hasText(domain, "网站资源目录域名不能为空");

            WebResource resource = RedisUtils.hget(jedisPool.getResource(), RedisConsts.WEB_RESOURCE, domain, WebResource.class);

            if(resource == null){
                resource = getByDomain(domain);

                if(resource != null && resource.getState().equals(UsingState.NORMAL)){
                    cache(resource);
                }else{
                    resource = null;
                }
            }

            return resource;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据域名删除缓存
     */
    public void deleteCacheByDomain(String domain){
        try{
            Assert.hasText(domain, "网站资源目录域名不能为空");

            if(RedisUtils.hexists(jedisPool.getResource(), RedisConsts.WEB_RESOURCE, domain)){
                List<String> fields = new ArrayList<String>();
                fields.add(domain);

                RedisUtils.hdel(jedisPool.getResource(), RedisConsts.WEB_RESOURCE, fields);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 更新缓存
     */
    public void updateCache(){
        try{
            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.state", UsingState.NORMAL.getId()));

            List<WebResource> list = mapper.findByParams(criteria);

            //删除缓存
            RedisUtils.delete(jedisPool.getResource(), RedisConsts.WEB_RESOURCE);
            //更新缓存
            Map<String, WebResource> map = new HashMap<String, WebResource>();
            for(WebResource resource : list){
                map.put(resource.getDomain(), resource);
            }
            RedisUtils.hmset(jedisPool.getResource(), RedisConsts.WEB_RESOURCE, map);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<WebResource> page(PageRequest pageRequest, UsingState state, String domain, String imgUrl, String cssUrl, String jsUrl, String organizationId, String organizationToken){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("w.createTime"));

            if(!StringUtils.isBlank(domain)){
                criteria.and(Restrictions.like("w.domain", domain));
            }
            if(!StringUtils.isBlank(imgUrl)){
                criteria.and(Restrictions.like("w.imgUrl", imgUrl));
            }
            if(!StringUtils.isBlank(cssUrl)){
                criteria.and(Restrictions.like("w.cssUrl", cssUrl));
            }
            if(!StringUtils.isBlank(jsUrl)){
                criteria.and(Restrictions.like("w.jsUrl", jsUrl));
            }
            if(state != null){
                criteria.and(Restrictions.eq("w.state", state.getId()));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("w.organizationId", organizationId));
            }
            if(!StringUtils.isBlank(organizationToken)){
                criteria.and(Restrictions.eq("o.token", organizationToken));
            }

            List<WebResource> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<WebResource>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 列表
     */
    public List<WebResource> list(UsingState state, String domain, String imgUrl, String cssUrl, String jsUrl, String organizationId, String organizationToken){
        try{
            Criteria criteria = new Criteria();
            criteria.or(Restrictions.asc("w.createTime"));

            if(!StringUtils.isBlank(domain)){
                criteria.and(Restrictions.like("w.domain", domain));
            }
            if(!StringUtils.isBlank(imgUrl)){
                criteria.and(Restrictions.like("w.imgUrl", imgUrl));
            }
            if(!StringUtils.isBlank(cssUrl)){
                criteria.and(Restrictions.like("w.cssUrl", cssUrl));
            }
            if(!StringUtils.isBlank(jsUrl)){
                criteria.and(Restrictions.like("w.jsUrl", jsUrl));
            }
            if(state != null){
                criteria.and(Restrictions.eq("w.state", state.getId()));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("w.organizationId", organizationId));
            }
            if(!StringUtils.isBlank(organizationToken)){
                criteria.and(Restrictions.eq("o.token", organizationToken));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
