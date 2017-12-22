package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.consts.PurchaseState;
import com.lab.hosaily.core.client.dao.AgreementDao;
import com.lab.hosaily.core.client.dao.PurchaseDao;
import com.lab.hosaily.core.client.entity.Agreement;
import com.lab.hosaily.core.client.entity.Purchase;
import com.lab.hosaily.core.product.dao.ServiceDao;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PurchaseServiceImpl implements PurchaseService{

    private static Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private AgreementDao agreementDao;

    @Autowired
    private ServiceDao serviceDao;

    /**
     * 保存
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Purchase purchase){
        try{
            Assert.notNull(purchase, "购买记录信息不能为空");
            Assert.notNull(purchase.getAgreement(), "协议信息不能为空");

            //保存购买记录
            purchaseDao.saveOrUpdate(purchase);
            //保存协议
            Agreement agreement = purchase.getAgreement();
            agreement.setPurchaseId(purchase.getId());
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
    public void update(Purchase purchase){
        try{
            Assert.notNull(purchase, "购买记录信息不能为空");
            Assert.hasText(purchase.getId(), "购买记录ID不能为空");
            Assert.notNull(purchase.getAgreement(), "协议信息不能为空");

            //更新购买记录
            purchaseDao.saveOrUpdate(purchase);
            //更新协议
            Agreement agreement = purchase.getAgreement();
            if(agreement != null && StringUtils.isBlank(agreement.getId())){
                agreementDao.saveOrUpdate(agreement);
                //更新服务
                serviceDao.deleteByMasterId(agreement.getId());
                serviceDao.save(agreement.getId(), agreement.getServices());
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Purchase getById(String id){
        try{
            Assert.hasText(id, "购买记录ID不能为空");

            return purchaseDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询
     */
    @Override
    public List<Purchase> list(PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price){
        try{
            return purchaseDao.list(purchaseState, state, accountId, organizationId, price);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页
     */
    @Override
    public Page<Purchase> page(PageRequest pageRequest, PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return purchaseDao.page(pageRequest, purchaseState, state, accountId, organizationId, price);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
