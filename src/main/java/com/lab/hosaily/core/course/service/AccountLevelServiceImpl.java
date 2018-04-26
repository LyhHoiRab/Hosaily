package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.dao.AccountLevelDao;
import com.lab.hosaily.core.course.entity.AccountLevel;
import com.rab.babylon.commons.security.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class AccountLevelServiceImpl implements AccountLevelService{

    private Logger logger = LoggerFactory.getLogger(AccountLevelServiceImpl.class);

    @Autowired
    private AccountLevelDao accountLevelDao;

    /**
     * 根据账户ID查询
     */
    @Override
    public AccountLevel getByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            return accountLevelDao.getByAccountId(accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
