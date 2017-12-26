package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.consts.PayState;
import com.lab.hosaily.core.client.consts.PayType;
import com.lab.hosaily.core.client.dao.mapper.PaymentMapper;
import com.lab.hosaily.core.client.entity.Payment;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class PaymentDao{

    private static Logger logger = LoggerFactory.getLogger(PaymentDao.class);

    @Autowired
    private PaymentMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Payment payment){
        try{
            Assert.notNull(payment, "支付记录信息不能为空");

            if(StringUtils.isBlank(payment.getId())){
                payment.setId(UUIDGenerator.by32());
                payment.setState(PayState.APPLY);
                payment.setCreateTime(new Date());
                mapper.save(payment);
            }else{
                payment.setUpdateTime(new Date());
                mapper.update(payment);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Payment getById(String id){
        try{
            Assert.hasText(id, "支付记录ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("p.id", id));

             return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据购买记录查询
     */
    public List<Payment> findByPurchaseId(String purchaseId){
        try{
            Assert.hasText(purchaseId, "购买记录ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("purchaseId", purchaseId));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    public List<Payment> list(String purchaseId, PayType type, PayState state){
        try{
            Criteria criteria = new Criteria();

            if(!StringUtils.isBlank(purchaseId)){
                criteria.and(Restrictions.eq("purchaseId", purchaseId));
            }
            if(type != null){
                criteria.and(Restrictions.eq("type", type.getId()));
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

    /**
     * 分页
     */
    public Page<Payment> page(PageRequest pageRequest, String purchaseId, PayType type, PayState state){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("payTime"));

            if(!StringUtils.isBlank(purchaseId)){
                criteria.and(Restrictions.eq("purchaseId", purchaseId));
            }
            if(type != null){
                criteria.and(Restrictions.eq("type", type.getId()));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }

            List<Payment> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Payment>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询金额
     */
    public long priceByPurchaseId(String purchaseId, PayType type, PayState state){
        try{
            Assert.hasText(purchaseId, "购买记录ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("purchaseId", purchaseId));
            criteria.groupBy(Restrictions.groupBy("purchaseId"));

            if(type != null){
                criteria.and(Restrictions.eq("type", type.getId()));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }

            return mapper.priceByPurchaseId(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
