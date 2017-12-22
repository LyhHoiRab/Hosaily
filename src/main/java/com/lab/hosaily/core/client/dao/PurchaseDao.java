package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.consts.PurchaseState;
import com.lab.hosaily.core.client.dao.mapper.PurchaseMapper;
import com.lab.hosaily.core.client.entity.Purchase;
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
public class PurchaseDao{

    private static Logger logger = LoggerFactory.getLogger(PurchaseDao.class);

    @Autowired
    private PurchaseMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Purchase purchase){
        try{
            Assert.notNull(purchase, "购买信息不能为空");

            if(StringUtils.isBlank(purchase.getId())){
                Assert.hasText(purchase.getOrganizationId(), "企业ID不能为空");
                Assert.hasText(purchase.getAccountId(), "账户ID不能为空");
                Assert.notNull(purchase.getAgreement(), "协议信息不能为空");

                purchase.setId(UUIDGenerator.by32());
                purchase.setOrderTime(new Date());
                purchase.setCreateTime(new Date());
                mapper.save(purchase);
            }else{
                purchase.setUpdateTime(new Date());
                mapper.update(purchase);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Purchase getById(String id){
        try{
            Assert.hasText(id, "购买记录ID不能为空");

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
    public List<Purchase> list(PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price){
        try{
            Criteria criteria = new Criteria();

            if(state != null){
                criteria.and(Restrictions.eq("p.state", state.getId()));
            }
            if(!StringUtils.isBlank(accountId)){
                criteria.and(Restrictions.eq("p.accountId", accountId));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("p.organizationId", organizationId));
            }
            if(price != null){
                criteria.and(Restrictions.ge("p.price", price));
            }
            if(purchaseState != null){
                criteria.and(Restrictions.eq("p.purchaseState", purchaseState.getId()));
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
    public Page<Purchase> page(PageRequest pageRequest, PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("p.orderTime"));
            criteria.groupBy(Restrictions.groupBy("p.id"));

            if(state != null){
                criteria.and(Restrictions.eq("p.state", state.getId()));
            }
            if(!StringUtils.isBlank(accountId)){
                criteria.and(Restrictions.eq("p.accountId", accountId));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("p.organizationId", organizationId));
            }
            if(price != null){
                criteria.and(Restrictions.ge("p.price", price));
            }
            if(purchaseState != null){
                criteria.and(Restrictions.eq("p.purchaseState", purchaseState.getId()));
            }

            List<String> ids = mapper.findIdByParams(criteria);
            Long count = mapper.countByParams(criteria);

            List<Purchase> list = new ArrayList<Purchase>();

            if(ids != null && !ids.isEmpty()){
                criteria.clear();
                criteria.sort(Restrictions.asc("p.orderTime"));
                criteria.and(Restrictions.in("p.id", ids));

                list = mapper.findByParams(criteria);
            }

            return new Page<Purchase>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
