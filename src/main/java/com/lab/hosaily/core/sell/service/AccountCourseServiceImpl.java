package com.lab.hosaily.core.sell.service;

import com.lab.hosaily.core.sell.dao.AccountCourseDao;
import com.lab.hosaily.core.sell.entity.AccountCourse;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountCourseServiceImpl implements AccountCourseService{

    private static Logger logger = LoggerFactory.getLogger(AccountCourseServiceImpl.class);

    @Autowired
    private AccountCourseDao accountCourseDao;

    /**
     * 保存
     */
    @Transactional(readOnly = false)
    @Override
    public void save(AccountCourse accountCourse){
        try{
            Assert.notNull(accountCourse, "账户关联课程信息不能为空");
            Assert.hasText(accountCourse.getAccountId(), "账户ID不能为空");
            Assert.notNull(accountCourse.getCourse(), "课程信息不能为空");
            Assert.hasText(accountCourse.getCourse().getId(), "课程ID不能为空");

            accountCourseDao.saveOrUpdate(accountCourse);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新
     */
    @Transactional(readOnly = false)
    @Override
    public void update(AccountCourse accountCourse){
        try{
            Assert.notNull(accountCourse, "账户关联课程信息不能为空");
            Assert.hasText(accountCourse.getId(), "账户关联课程信息ID不能为空");

            accountCourseDao.saveOrUpdate(accountCourse);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    @Transactional(readOnly = false)
    @Override
    public void delete(String id){
        try{
            Assert.hasText(id, "账户关联课程ID不能为空");

            accountCourseDao.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<AccountCourse> page(PageRequest pageRequest, String accountId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return accountCourseDao.page(pageRequest, accountId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
