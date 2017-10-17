package com.lab.hosaily.core.account.dao;

import com.lab.hosaily.core.account.consts.AttentionType;
import com.lab.hosaily.core.account.dao.mapper.AttentionMapper;
import com.lab.hosaily.core.account.entity.Attention;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class AttentionDao{

    private static Logger logger = LoggerFactory.getLogger(AttentionDao.class);

    @Autowired
    private AttentionMapper mapper;

    /**
     * 保存或更新
     */
    public void save(Attention attention){
        try{
            Assert.notNull(attention, "课程关注信息不能为空");
            Assert.hasText(attention.getAccountId(), "账户ID不能为空");
            Assert.notNull(attention.getCourse(), "课程信息不能为空");
            Assert.hasText(attention.getCourse().getId(), "课程ID不能为空");
            Assert.notNull(attention.getType(), "关注类型不能为空");

            attention.setId(UUIDGenerator.by32());
            attention.setCreateTime(new Date());
            mapper.save(attention);
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
            Assert.hasText(id, "关注ID不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询足迹记录
     */
    public List<Attention> findTrackByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.accountId", accountId));
            criteria.and(Restrictions.eq("a.type", AttentionType.TRACK.getId()));
            criteria.sort(Restrictions.desc("a.createTime"));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询收藏记录
     */
    public List<Attention> findCollectByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.accountId", accountId));
            criteria.and(Restrictions.eq("a.type", AttentionType.COLLECT.getId()));
            criteria.sort(Restrictions.desc("a.createTime"));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 判断账户是否有收藏或足迹记录
     */
    public Boolean existByAccountIdAndCourseId(String accountId, String courseId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");
            Assert.hasText(courseId, "课程ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.accountId", accountId));
            criteria.and(Restrictions.eq("a.courseId", courseId));

            return mapper.existByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
