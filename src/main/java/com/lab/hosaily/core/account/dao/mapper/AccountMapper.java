package com.lab.hosaily.core.account.dao.mapper;

import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.core.account.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountMapper{

    /**
     * 保存记录
     */
    void save(Account account);

    /**
     * 更新记录
     */
    void update(Account account);

    /**
     * 删除记录
     * 逻辑删除
     */
    void delete(String id);

    /**
     * 查询单条记录
     */
    Account getByParams(@Param("params") Criteria criteria);

    /**
     * 查询多条记录
     */
    List<Account> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
