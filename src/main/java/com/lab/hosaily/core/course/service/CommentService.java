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
     * 分页查询
     */
    Page<Comment> page(PageRequest pageRequest, String senderId, String courseId);
}
