package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.consts.PayState;
import com.lab.hosaily.core.client.consts.PayType;
import com.lab.hosaily.core.client.dao.PaymentDao;
import com.rab.babylon.commons.security.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService{

    private static Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentDao paymentDao;

    /**
     * 根据购买记录统计已成功付款金额
     */
    @Override
    public double priceByPurchaseId(String purchaseId){
        try{
            Assert.hasText(purchaseId, "购买记录ID不能为空");

            return paymentDao.priceByPurchaseId(purchaseId, null, PayState.PAID);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
