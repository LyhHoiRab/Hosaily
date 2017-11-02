package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.dao.TagDao;
import com.lab.hosaily.core.course.entity.Tag;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService{

    private static Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

    @Autowired
    private TagDao tagDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Tag tag){
        try{
            Assert.notNull(tag, "标签信息不能为空");

            tagDao.saveOrUpdate(tag);
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
    public void update(Tag tag){
        try{
            Assert.notNull(tag, "标签信息不能为空");
            Assert.hasText(tag.getId(), "标签ID不能为空");

            tagDao.saveOrUpdate(tag);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    @Override
    public Tag getById(String id){
        try{
            Assert.hasText(id, "标签信息不能为空");

            return tagDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据状态查询记录
     */
    @Override
    public List<Tag> findByState(UsingState state){
        try{
            Assert.notNull(state, "标签状态不能为空");

            return tagDao.findByState(state);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    @Override
    public List<Tag> list(UsingState state){
        try{
            return tagDao.findByState(state);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Tag> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return tagDao.page(pageRequest);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
