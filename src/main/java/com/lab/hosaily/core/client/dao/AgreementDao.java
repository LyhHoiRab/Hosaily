package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.dao.mapper.AgreementMapper;
import com.lab.hosaily.core.client.entity.Agreement;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;

@Repository
public class AgreementDao{

    private static Logger logger = LoggerFactory.getLogger(AgreementDao.class);

    @Autowired
    private AgreementMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Agreement agreement){
        try{
            Assert.notNull(agreement, "协议信息不能为空");

            if(StringUtils.isBlank(agreement.getId())){
                Assert.hasText(agreement.getPurchaseId(), "购买记录ID不能为空");
//                Assert.hasText(agreement.getAccountId(), "账户ID不能为空");
                Assert.hasText(agreement.getClient(), "客户名称不能为空");
//                Assert.hasText(agreement.getPhone(), "联系电话不能为空");
                Assert.hasText(agreement.getWechat(), "微信不能为空");

                agreement.setId(UUIDGenerator.by32());
                agreement.setCreateTime(new Date());
                agreement.setState(UsingState.NORMAL);
                mapper.save(agreement);
            }else{
                agreement.setUpdateTime(new Date());
                mapper.update(agreement);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Agreement getById(String id){
        try{
            Assert.hasText(id, "协议ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据购买记录ID查询
     */
    public Agreement getByPurchaseId(String purchaseId){
        try{
            Assert.hasText(purchaseId, "购买记录ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.purchaseId", purchaseId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
