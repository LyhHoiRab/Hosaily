package com.lab.hosaily.core.sell.dao;

import com.lab.hosaily.core.sell.dao.mapper.PaymentTypeMapper;
import com.lab.hosaily.core.sell.entity.PaymentType;
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
public class PaymentTypeDao{

    private static Logger logger = LoggerFactory.getLogger(PaymentTypeDao.class);

    @Autowired
    private PaymentTypeMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(PaymentType paymentType){
        try{
            Assert.notNull(paymentType, "支付方式信息不能为空");

            if(StringUtils.isBlank(paymentType.getId())){
                paymentType.setId(UUIDGenerator.by32());
                paymentType.setCreateTime(new Date());
                mapper.save(paymentType);
            }else{
                paymentType.setUpdateTime(new Date());
                mapper.update(paymentType);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public PaymentType getById(String id){
        try{
            Assert.hasText(id, "支付方式ID不能为空");

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
    public Page<PaymentType> page(PageRequest pageRequest, UsingState state, String name){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }

            List<PaymentType> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<PaymentType>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<PaymentType> list(UsingState state, String name){
        try{
            Criteria criteria = new Criteria();

            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
