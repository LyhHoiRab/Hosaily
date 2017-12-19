package com.lab.hosaily.core.product.service;

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
public class ServiceServiceImpl implements ServiceService{

    private static Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);

    @Autowired
    private ServiceDao serviceDao;

    /**
     * 删除
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "服务ID不能为空");

            serviceDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
