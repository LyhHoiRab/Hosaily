package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Advisor;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisorMapper{

    /**
     * 保存记录
     */
    void save(Advisor advisor);

    /**
     * 更新记录
     */
    void update(Advisor advisor);

    /**
     * 根据条件查询单条记录
     */
    Advisor getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<Advisor> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    Long countByParams(@Param("params") Criteria criteria);
}
