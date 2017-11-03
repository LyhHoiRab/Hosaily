package com.lab.hosaily.core.sell.dao;

import com.lab.hosaily.core.sell.consts.CourseConsts;
import com.lab.hosaily.core.sell.dao.mapper.AccountCourseMapper;
import com.lab.hosaily.core.sell.entity.AccountCourse;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.DateUtils;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
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
                Assert.notNull(accountCourse.getCourse(), "课程信息不能为空");
                Assert.hasText(accountCourse.getCourse().getId(), "课程ID不能为空");

                if(accountCourse.getForceTime() == null){
                    accountCourse.setForceTime(new Date());
                }
                if(accountCourse.getEffective() == null || accountCourse.getEffective() < 0){
                    accountCourse.setEffective(CourseConsts.MAX_EFFECTIVE);
                }

                accountCourse.setDeadline(DateUtils.addDays(accountCourse.getForceTime(), accountCourse.getEffective()));
                accountCourse.setId(UUIDGenerator.by32());
                accountCourse.setState(UsingState.NORMAL);
                accountCourse.setCreateTime(new Date());
                mapper.save(accountCourse);
            }else{
                if(accountCourse.getEffective() != null && accountCourse.getForceTime() != null){
                    accountCourse.setDeadline(DateUtils.addDays(accountCourse.getForceTime(), accountCourse.getEffective()));
                }

                accountCourse.setUpdateTime(new Date());
                mapper.update(accountCourse);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    public void delete(String id){
        try{
            Assert.hasText(id, "账户关联课程ID不能为空");

            mapper.delete(id);
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

    /**
     * 分页查询
     */
    public Page<AccountCourse> page(PageRequest pageRequest, String accountId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.groupBy(Restrictions.groupBy("ac.id"));
            criteria.sort(Restrictions.asc("ac.forceTime"));

            if(!StringUtils.isBlank(accountId)){
                criteria.and(Restrictions.eq("ac.accountId", accountId));
            }

            List<String> ids = mapper.findIdByParams(criteria);
            Long count = mapper.countByParams(criteria);

            List<AccountCourse> list = new ArrayList<AccountCourse>();

            if(!ids.isEmpty()){
                criteria.clear();
                criteria.and(Restrictions.in("ac.id", ids));
                criteria.sort(Restrictions.asc("ac.forceTime"));

                list.addAll(mapper.findByParams(criteria));
            }

            return new Page<AccountCourse>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
