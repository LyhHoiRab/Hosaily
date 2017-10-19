package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.CommentMapper;
import com.lab.hosaily.core.course.entity.Comment;
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
public class CommentDao{

    private static Logger logger = LoggerFactory.getLogger(CommentDao.class);

    @Autowired
    private CommentMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Comment comment){
        try{
            Assert.notNull(comment, "评论信息不能为空");

            if(StringUtils.isBlank(comment.getId())){
                Assert.notNull(comment.getSender(), "评论用户信息不能为空");
                Assert.hasText(comment.getSender().getId(), "评论用户ID不能为空");
                Assert.hasText(comment.getCourseId(), "课程ID不能为空");
                Assert.hasText(comment.getContent(), "评论内容不能为空");

                comment.setId(UUIDGenerator.by32());
                comment.setState(UsingState.NORMAL);
                comment.setCreateTime(new Date());
                mapper.save(comment);
            }else{
                comment.setUpdateTime(new Date());
                mapper.update(comment);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询评论
     */
    public Comment getById(String id){
        try{
            Assert.hasText(id, "评论ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据用户ID查询评论
     */
    public List<Comment> findBySenderId(String senderId){
        try{
            Assert.hasText(senderId, "评论用户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.senderId", senderId));
            criteria.sort(Restrictions.desc("c.createTime"));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据用户ID分页查询
     */
    public Page<Comment> pageBySenderId(PageRequest pageRequest, String senderId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");
            Assert.hasText(senderId, "评论用户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.senderId", senderId));
            criteria.sort(Restrictions.desc("c.createTime"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<Comment> list = mapper.findByParams(criteria);

            return new Page<Comment>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据课程ID查询评论
     */
    public List<Comment> findByCourseId(String courseId){
        try{
            Assert.hasText(courseId, "课程ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.courseId", courseId));
            criteria.sort(Restrictions.desc("c.createTime"));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据课程ID分页查询
     */
    public Page<Comment> pageByCourseId(PageRequest pageRequest, String courseId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");
            Assert.hasText(courseId, "课程ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.courseId", courseId));
            criteria.sort(Restrictions.desc("c.createTime"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<Comment> list = mapper.findByParams(criteria);

            return new Page<Comment>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据课程ID查询数量
     */
    public Long countByCourseId(String courseId){
        try{
            Assert.hasText(courseId, "课程ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("c.courseId", courseId));

            return mapper.countByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
