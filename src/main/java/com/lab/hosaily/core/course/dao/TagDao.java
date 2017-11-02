package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.TagMapper;
import com.lab.hosaily.core.course.entity.Tag;
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
public class TagDao{

    private static Logger logger = LoggerFactory.getLogger(TagDao.class);

    @Autowired
    private TagMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Tag tag){
        try{
            Assert.notNull(tag, "标签信息不能为空");

            if(StringUtils.isBlank(tag.getId())){
                Assert.hasText(tag.getName(), "标签名称不能为空");

                tag.setId(UUIDGenerator.by32());
                tag.setState(UsingState.NORMAL);
                tag.setCreateTime(new Date());
                mapper.save(tag);
            }else{
                tag.setUpdateTime(new Date());
                mapper.update(tag);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除标签
     */
    public void delete(String id){
        try{
            Assert.hasText(id, "标签ID不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询标签
     */
    public Tag getById(String id){
        try{
            Assert.hasText(id, "标签ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据状态查询
     */
    public List<Tag> findByState(UsingState state){
        try{
            Assert.notNull(state, "标签状态不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("state", state.getId()));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Tag> list(UsingState state){
        try{
            Criteria criteria = new Criteria();

            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Tag> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<Tag> list = mapper.findByParams(criteria);

            return new Page<Tag>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
