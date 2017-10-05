package com.lab.hosaily.core.curricula.dao;

import com.lab.hosaily.core.curricula.dao.mapper.MediaMapper;
import com.lab.hosaily.core.curricula.entity.Media;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class MediaDao{

    private static Logger logger = LoggerFactory.getLogger(MediaDao.class);

    @Autowired
    private MediaMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Media media){
        try{
            Assert.notNull(media, "媒体信息不能为空");

            if(StringUtils.isBlank(media.getId())){
                Assert.hasText(media.getMd5(), "媒体MD5不能为空");

                media.setId(UUIDGenerator.by32());
                media.setState(UsingState.NORMAL);
                media.setCreateTime(new Date());
                mapper.save(media);
            }else{
                media.setUpdateTime(new Date());
                mapper.update(media);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    public Media getById(String id){
        try{
            Assert.hasText(id, "媒体ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据MD5查询记录
     */
    public Media getByMd5(String md5){
        try{
            Assert.hasText(md5, "md5值不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("md5", md5));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Media> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<Media> list = mapper.findByParams(criteria);

            return new Page<Media>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
