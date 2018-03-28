package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Project;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {

    /**
     * 保存记录
     */
    void save(Project project);

    /**
     * 更新记录
     */
    void update(Project project);

    /**
     * 删除记录
     */
    void delete(String id);

    /**
     * 根据条件查询单条记录
     */
    Project getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<Project> findByParams(@Param("params") Criteria criteria);


    List<Project> findByParamsByPage(@Param("accountId") String accountId, @Param("offset") Long offset, @Param("pageSize") Long pageSize);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
