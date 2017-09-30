package com.lab.hosaily.core.account.dao;

import com.lab.hosaily.core.account.dao.mapper.AccountMapper;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.core.account.entity.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class AccountDao{

    private static Logger logger = LoggerFactory.getLogger(AccountDao.class);

    @Autowired
    private AccountMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Account account){
        try{

        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Account getById(String id){
        try{
            Assert.hasText(id, "账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据微信账号查询
     */
    public Account getByWeChat(String weChat){
        try{
            Assert.hasText(weChat, "微信账号不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("weChat", weChat));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
