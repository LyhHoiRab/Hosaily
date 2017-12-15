package com.lab.hosaily.core.product.service;

import com.lab.hosaily.core.product.dao.ProductDao;
import com.lab.hosaily.core.product.entity.Product;
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
public class ProductServiceImpl implements ProductService{

    private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Product product){
        try{
            Assert.notNull(product, "产品信息不能为空");

            productDao.saveOrUpdate(product);
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
    public void update(Product product){
        try{
            Assert.notNull(product, "产品信息不能为空");
            Assert.hasText(product.getId(), "产品ID不能为空");

            productDao.saveOrUpdate(product);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    @Override
    public Product getById(String id){
        try{
            Assert.hasText(id, "产品ID不能为空");

            return productDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    @Override
    public List<Product> list(UsingState state, String name, Double price, Integer duration, String organizationId){
        try{
            return productDao.list(state, name, price, duration, organizationId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @Override
    public Page<Product> page(PageRequest pageRequest, UsingState state, String name, Double price, Integer duration, String organizationId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return productDao.page(pageRequest, state, name, price, duration, organizationId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
