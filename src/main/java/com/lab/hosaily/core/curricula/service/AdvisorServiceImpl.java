package com.lab.hosaily.core.curricula.service;

import com.lab.hosaily.core.curricula.dao.AdvisorDao;
import com.lab.hosaily.core.curricula.entity.Advisor;
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
public class AdvisorServiceImpl implements AdvisorService{

    private static Logger logger = LoggerFactory.getLogger(AdvisorServiceImpl.class);

    @Autowired
    private AdvisorDao advisorDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Advisor advisor){
        try{
            Assert.notNull(advisor, "导师信息不能为空");

            advisorDao.saveOrUpdate(advisor);
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
    public void update(Advisor advisor){
        try{
            Assert.notNull(advisor, "导师信息不能为空");
            Assert.hasText(advisor.getId(), "导师ID不能为空");

            advisorDao.saveOrUpdate(advisor);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Advisor getById(String id){
        try{
            Assert.hasText(id, "导师ID不能为空");

            return advisorDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Advisor> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return advisorDao.page(pageRequest);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
