package com.lab.hosaily.core.account.dao.mapper;

import com.lab.hosaily.core.account.entity.AppAccount;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppAccountMapper {

    /**
     * 保存记录
     */
    void save(AppAccount account);

    /**
     * 更新记录
     */
    void update(AppAccount account);

    /**
     * 根据条件查询单条记录
     */
    AppAccount getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<AppAccount> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
