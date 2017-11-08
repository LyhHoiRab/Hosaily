package com.lab.hosaily.core.popularize.dao;

import com.lab.hosaily.core.popularize.dao.mapper.TestLogsMapper;
import com.lab.hosaily.core.popularize.entity.TestLogs;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class TestLogsDao{

    private static Logger logger = LoggerFactory.getLogger(TestLogsDao.class);

    @Autowired
    private TestLogsMapper mapper;

    /**
     * 保存
     */
    public void save(TestLogs logs){
        try{
            Assert.notNull(logs, "测试记录不能为空");
            Assert.hasText(logs.getAccountId(), "账户ID不能为空");
            Assert.hasText(logs.getTestId(), "题库ID不能为空");

            logs.setCreateTime(new Date());
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询记录
     */
    public TestLogs getByParams(String accountId, Date createTime){
        try{
            Criteria criteria = new Criteria();

            if(!StringUtils.isBlank(accountId)){
                criteria.and(Restrictions.eq("accountId", accountId));
            }
            if(createTime != null){
                Date minCreateTime = DateUtils.firstTimeOfDay(createTime);
                Date maxCreateTime = DateUtils.lastTimeOfDay(createTime);
                criteria.and(Restrictions.between("createTime", minCreateTime, maxCreateTime));
            }

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询记录
     */
    public List<TestLogs> findByParams(String accountId, Date createTime){
        try{
            Criteria criteria = new Criteria();

            if(!StringUtils.isBlank(accountId)){
                criteria.and(Restrictions.eq("accountId", accountId));
            }
            if(createTime != null){
                Date minCreateTime = DateUtils.firstTimeOfDay(createTime);
                Date maxCreateTime = DateUtils.lastTimeOfDay(createTime);
                criteria.and(Restrictions.between("createTime", minCreateTime, maxCreateTime));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
