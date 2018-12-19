package com.lab.hosaily.core.app.dao.mapper;

import com.lab.hosaily.core.app.entity.FeedBack;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackMapper {

    /**
     * 保存记录
     */
    void save(FeedBack news);

    /**
     * 更新记录
     */
    void update(FeedBack news);

    /**
     * 根据条件查询单条记录
     */
    FeedBack getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<FeedBack> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    Long countByParams(@Param("params") Criteria criteria);
}
