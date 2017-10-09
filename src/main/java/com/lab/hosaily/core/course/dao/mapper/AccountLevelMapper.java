package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.AccountLevel;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountLevelMapper{

    /**
     * 保存记录
     */
    void save(AccountLevel accountLevel);

    /**
     * 更新记录
     */
    void update(AccountLevel accountLevel);

    /**
     * 根据条件查询单条记录
     */
    AccountLevel getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<AccountLevel> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
