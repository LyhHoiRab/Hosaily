package com.lab.hosaily.core.curricula.service;

import com.lab.hosaily.core.curricula.dao.LevelPriceDao;
import com.rab.babylon.commons.security.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class LevelPriceServiceImpl implements LevelPriceService{

    private static Logger logger = LoggerFactory.getLogger(LevelPriceServiceImpl.class);

    @Autowired
    private LevelPriceDao levelPriceDao;

    /**
     * 删除记录
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(String id){
        try{
            Assert.hasText(id, "价格ID不能为空");

            levelPriceDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
