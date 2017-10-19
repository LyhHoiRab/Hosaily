package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Comment;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;

import java.util.List;

public interface CommentService{

    /**
     * 保存记录
     */
    void save(Comment comment);

    /**
     * 更新记录
     */
    void update(Comment comment);

    /**
     * 根据ID查询
     */
    Comment getById(String id);

    /**
     * 根据用户ID查询
     */
    List<Comment> findBySenderId(String senderId);

    /**
     * 根据用户ID分页查询
     */
    Page<Comment> pageBySenderId(PageRequest pageRequest, String senderId);

    /**
     * 根据课程ID查询
     */
    List<Comment> findByCourseId(String courseId);

    /**
     * 根据课程ID分页查询
     */
    Page<Comment> pageByCourseId(PageRequest pageRequest, String courseId);

    /**
     * 根据课程ID查询数量
     */
    Long countByCourseId(String courseId);
}
