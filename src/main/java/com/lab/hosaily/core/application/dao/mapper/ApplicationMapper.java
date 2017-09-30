package com.lab.hosaily.core.application.dao.mapper;

import com.lab.hosaily.core.application.entity.Application;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationMapper{

    /**
     * 保存记录
     */
    void save(Application application);

    /**
     * 更新记录
     */
    void update(Application application);

    /**
     * 删除记录
     * 逻辑删除
     */
    void delete(String id);

    /**
     * 查询单条记录
     */
    Application getByParams(@Param("params") Criteria criteria);

    /**
     * 查询多条记录
     */
    List<Application> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
