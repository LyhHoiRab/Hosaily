package com.lab.hosaily.core.curricula.dao.mapper;

import com.lab.hosaily.core.curricula.entity.LevelPrice;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelPriceMapper{

    /**
     * 保存记录
     */
    void save(LevelPrice price);

    /**
     * 批量保存
     */
    void saveByBatch(@Param("prices") List<LevelPrice> prices);

    /**
     * 更新记录
     */
    void update(LevelPrice price);

    /**
     * 批量更新
     */
    void updateByBatch(@Param("prices") List<LevelPrice> prices);

    /**
     * 删除记录
     */
    void delete(String id);

    /**
     * 查询单条记录
     */
    LevelPrice getByParams(@Param("params") Criteria criteria);

    /**
     * 查询多条记录
     */
    List<LevelPrice> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
