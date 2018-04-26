package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.AccountLevelMapper;
import com.lab.hosaily.core.course.entity.AccountLevel;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.DateUtils;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;

@Repository
public class AccountLevelDao{

    private Logger logger = LoggerFactory.getLogger(AccountLevelDao.class);

    @Autowired
    private AccountLevelMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(AccountLevel accountLevel){
        try{
            Assert.notNull(accountLevel, "会员购买信息不能为空");

            if(StringUtils.isBlank(accountLevel.getId())){
                Assert.hasText(accountLevel.getAccountId(), "账户ID不能为空");

                //当天生效
                final Date now = new Date();
                //默认365天有效期
                int effective = 365;

                accountLevel.setId(UUIDGenerator.by32());
                accountLevel.setCreateTime(now);
                accountLevel.setStartTime(now);
                accountLevel.setEffective(effective);
                accountLevel.setEndTime(DateUtils.addDays(now, effective));
                accountLevel.setIsValid(true);
                mapper.save(accountLevel);
            }else{
                accountLevel.setUpdateTime(new Date());
                mapper.save(accountLevel);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询
     */
    public AccountLevel getByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("accountId", accountId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
