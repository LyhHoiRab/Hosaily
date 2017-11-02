package com.lab.hosaily.core.sell.dao;

import com.lab.hosaily.core.sell.dao.mapper.AccountCourseMapper;
import com.lab.hosaily.core.sell.entity.AccountCourse;
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
public class AccountCourseDao{

    private static Logger logger = LoggerFactory.getLogger(AccountCourseDao.class);

    @Autowired
    private AccountCourseMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(AccountCourse accountCourse){
        try{
            Assert.notNull(accountCourse, "账户课程权限信息不能为空");

            if(StringUtils.isBlank(accountCourse.getId())){
                Assert.hasText(accountCourse.getAccountId(), "账户ID不能为空");
                Assert.hasText(accountCourse.getCourseId(), "课程ID不能为空");

                accountCourse.setId(UUIDGenerator.by32());
                accountCourse.setCreateTime(new Date());
                mapper.save(accountCourse);
            }else{
                accountCourse.setUpdateTime(new Date());
                mapper.update(accountCourse);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询
     */
    public List<AccountCourse> findByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("accountId", accountId));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
