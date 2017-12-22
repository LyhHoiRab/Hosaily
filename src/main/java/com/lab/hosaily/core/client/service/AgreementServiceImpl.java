package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.dao.AgreementDao;
import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.product.dao.ServiceDao;
import com.rab.babylon.commons.security.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class AgreementServiceImpl implements AgreementService{

    private static Logger logger = LoggerFactory.getLogger(AgreementServiceImpl.class);

    @Autowired
    private AgreementDao agreementDao;

    @Autowired
    private ServiceDao serviceDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Agreement agreement){
        try{
            Assert.notNull(agreement, "协议信息不能为空");

            //保存协议
            agreementDao.saveOrUpdate(agreement);
            //保存服务
            serviceDao.save(agreement.getId(), agreement.getServices());
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
    public void update(Agreement agreement){
        try{
            Assert.notNull(agreement, "协议信息不能为空");
            Assert.hasText(agreement.getId(), "协议ID不能为空");

            //更新协议
            agreementDao.saveOrUpdate(agreement);
            //更新服务
            serviceDao.deleteByMasterId(agreement.getId());
            serviceDao.save(agreement.getId(), agreement.getServices());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Agreement getById(String id){
        try{
            Assert.hasText(id, "协议ID不能为空");

            return agreementDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据购买ID查询
     */
    @Override
    public Agreement getByPurchaseId(String purchaseId){
        try{
            Assert.hasText(purchaseId, "购买记录ID不能为空");

            return agreementDao.getByPurchaseId(purchaseId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
