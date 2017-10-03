package com.lab.hosaily.core.account.dao.mapper;

import com.lab.hosaily.core.account.entity.XcxAccount;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface XcxAccountMapper{

    /**
     * 保存记录
     */
    void save(XcxAccount account);

    /**
     * 更新记录
     */
    void update(XcxAccount account);

    /**
     * 根据条件查询单条记录
     */
    XcxAccount getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<XcxAccount> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
