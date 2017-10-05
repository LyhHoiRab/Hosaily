package com.lab.hosaily.core.curricula.dao.mapper;

import com.lab.hosaily.core.curricula.entity.Media;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaMapper{

    /**
     * 保存记录
     */
    void save(Media media);

    /**
     * 更新记录
     */
    void update(Media media);

    /**
     * 查询单条记录
     */
    Media getByParams(@Param("params") Criteria criteria);

    /**
     * 查询多条记录
     */
    List<Media> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
