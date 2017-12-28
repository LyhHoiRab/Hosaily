package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.dao.mapper.AlipayMapper;
import com.lab.hosaily.core.client.entity.Alipay;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;

@Repository
public class AlipayDao{

    private static Logger logger = LoggerFactory.getLogger(AlipayDao.class);

    @Autowired
    private AlipayMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Alipay alipay){
        try{
            Assert.notNull(alipay, "支付宝支付记录信息不能为空");

            if(StringUtils.isBlank(alipay.getId())){
                alipay.setId(UUIDGenerator.by32());
                alipay.setCreateTime(new Date());
                mapper.save(alipay);
            }else{
                alipay.setUpdateTime(new Date());
                mapper.update(alipay);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Alipay getById(String id){
        try{
            Assert.hasText(id, "支付宝支付记录ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据商户订单号查询
     */
    public Alipay getByOutTradeNo(String outTradeNo){
        try{
            Assert.hasText(outTradeNo, "商户订单号不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("outTradeNo", outTradeNo));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
