package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.dao.mapper.WechatMerchantPayMapper;
import com.lab.hosaily.core.client.entity.WechatMerchantPay;
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
import java.util.List;

@Repository
public class WechatMerchantPayDao{

    private static Logger logger = LoggerFactory.getLogger(WechatMerchantPayDao.class);

    @Autowired
    private WechatMerchantPayMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(WechatMerchantPay wechatMerchantPay){
        try{
            Assert.notNull(wechatMerchantPay, "微信商户支付记录信息不能为空");

            if(StringUtils.isBlank(wechatMerchantPay.getId())){
                wechatMerchantPay.setId(UUIDGenerator.by32());
                wechatMerchantPay.setCreateTime(new Date());
                mapper.save(wechatMerchantPay);
            }else{
                wechatMerchantPay.setUpdateTime(new Date());
                mapper.update(wechatMerchantPay);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public WechatMerchantPay getById(String id){
        try{
            Assert.hasText(id, "微信商户支付记录ID不能为空");

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
    public WechatMerchantPay getByOutTradeNo(String outTradeNo){
        try{
            Assert.hasText(outTradeNo, "支付记录商户订单号不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("outTradeNo", outTradeNo));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
