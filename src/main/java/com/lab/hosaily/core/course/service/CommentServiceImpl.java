package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.dao.CommentDao;
import com.lab.hosaily.core.course.entity.Comment;
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
public class CommentServiceImpl implements CommentService{

    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    /**
     * 保存记录
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Comment comment){
        try{
            Assert.notNull(comment, "评论信息不能为空");
            Assert.notNull(comment.getSender(), "评论用户信息不能为空");
            Assert.hasText(comment.getSender().getId(), "评论用户ID不能为空");
            Assert.hasText(comment.getCourseId(), "课程ID不能为空");
            Assert.hasText(comment.getContent(), "评论内容不能为空");

            commentDao.saveOrUpdate(comment);
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
    public void update(Comment comment){
        try{
            Assert.notNull(comment, "评论信息不能为空");
            Assert.hasText(comment.getId(), "评论ID不能为空");

            commentDao.saveOrUpdate(comment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Comment getById(String id){
        try{
            Assert.hasText(id, "评论ID不能为空");

            return commentDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Comment> page(PageRequest pageRequest, String senderId, String courseId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return commentDao.pageBySenderId(pageRequest, senderId, courseId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
