package com.lab.hosaily.core.organization.dao.mapper;

import com.lab.hosaily.core.organization.entity.Organization;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationMapper{

    /**
     * 保存
     */
    void save(Organization organization);

    /**
     * 更新
     */
    void update(Organization organization);

    /**
     * 查询
     */
    Organization getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<Organization> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
