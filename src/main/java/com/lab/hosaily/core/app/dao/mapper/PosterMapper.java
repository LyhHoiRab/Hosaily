package com.lab.hosaily.core.app.dao.mapper;

import com.lab.hosaily.core.app.entity.Poster;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosterMapper {

    /**
     * 保存记录
     */
    void save(Poster poster);

    /**
     * 更新记录
     */
    void update(Poster poster);

    /**
     * 根据条件查询单条记录
     */
    Poster getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<Poster> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    Long countByParams(@Param("params") Criteria criteria);
}
