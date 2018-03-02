package com.lab.hosaily.core.product.dao;

import com.lab.hosaily.core.product.dao.mapper.ProductMapper;
import com.lab.hosaily.core.product.entity.Product;
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
public class ProductDao{

    private static Logger logger = LoggerFactory.getLogger(ProductDao.class);

    @Autowired
    private ProductMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Product product){
        try{
            Assert.notNull(product, "产品信息不能为空");

            if(StringUtils.isBlank(product.getId())){
                product.setId(UUIDGenerator.by32());
                product.setCreateTime(new Date());
                mapper.save(product);
            }else{
                product.setUpdateTime(new Date());
                mapper.update(product);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    public Product getById(String id){
        try{
            Assert.hasText(id, "产品ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("p.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    public List<Product> list(UsingState state, String name, Double price, Integer duration, String organizationId){
        try{
            Criteria criteria = new Criteria();

            if(state != null){
                criteria.and(Restrictions.eq("p.state", state.getId()));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("p.name", name));
            }
            if(price != null){
                criteria.and(Restrictions.ge("p.price", price));
            }
            if(duration != null){
                criteria.and(Restrictions.ge("p.duration", duration));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("p.organizationId", organizationId));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    public Page<Product> page(PageRequest pageRequest, UsingState state, String name, Double price, Integer duration, String organizationId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("p.createTime"));
            criteria.groupBy(Restrictions.groupBy("p.id"));

            if(state != null){
                criteria.and(Restrictions.eq("p.state", state.getId()));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("p.name", name));
            }
            if(price != null){
                criteria.and(Restrictions.ge("p.price", price));
            }
            if(duration != null){
                criteria.and(Restrictions.ge("p.duration", duration));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("p.organizationId", organizationId));
            }

            List<Product> list = null;
            List<String> ids = mapper.findIdByParams(criteria);
            Long count = mapper.countByParams(criteria);

            if(ids != null && !ids.isEmpty()){
                criteria.clear();
                criteria.and(Restrictions.in("p.id", ids));
                criteria.sort(Restrictions.asc("p.createTime"));

                list = mapper.findByParams(criteria);
            }

            return new Page<Product>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
