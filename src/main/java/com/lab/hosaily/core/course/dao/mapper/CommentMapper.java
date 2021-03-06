package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Comment;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper{

    /**
     * 保存记录
     */
    void save(Comment comment);

    /**
     * 更新记录
     */
    void update(Comment comment);

    /**
     * 删除评论
     */
    void delete(String id);

    /**
     * 根据条件查询单条记录
     */
    Comment getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<Comment> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
