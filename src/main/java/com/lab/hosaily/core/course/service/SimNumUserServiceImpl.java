package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.dao.SimNumUserDao;
import com.lab.hosaily.core.course.entity.SimNumUser;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly = true)
public class SimNumUserServiceImpl implements SimNumUserService{

    private static Logger logger = LoggerFactory.getLogger(SimNumUserServiceImpl.class);

    @Autowired
    private SimNumUserDao simNumUserDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(SimNumUser simNumUser){
        try{
            Assert.notNull(simNumUser, "标签信息不能为空");

            simNumUserDao.saveOrUpdate(simNumUser);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新记录
     */
    @Override
    @Transactional(readOnly = false)
    public void update(SimNumUser simNumUser){
        try{
            Assert.notNull(simNumUser, "标签信息不能为空");
            Assert.hasText(simNumUser.getSim(), "标签sim不能为空");

            simNumUserDao.saveOrUpdate(simNumUser);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public SimNumUser getBySim(String sim){
        try{
            Assert.hasText(sim, "标签信息不能为空");

            return simNumUserDao.getBySim(sim);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<SimNumUser> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return simNumUserDao.page(pageRequest);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
