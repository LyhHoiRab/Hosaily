package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Level;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelMapper{

    /**
     * 保存记录
     */
    void save(Level level);

    /**
     * 更新记录
     */
    void update(Level level);

    /**
     * 删除记录
     * 逻辑删除
     */
    void delete(String id);

    /**
     * 查询单条记录
     */
    Level getByParams(@Param("params") Criteria criteria);

    /*
     * 根据条件查询ID
     */
    List<String> findIdByParams(@Param("params") Criteria criteria);

    /**
     * 查询多条记录
     */
    List<Level> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
