package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Question;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {

    /**
     * 保存记录
     */
    void save(Question question);

    /**
     * 更新记录
     */
    void update(Question question);

    /**
     * 删除记录
     */
    void delete(String id);

    /**
     * 根据条件查询单条记录
     */
    Question getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<Question> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
