package com.lab.hosaily.core.account.dao.mapper;

import com.lab.hosaily.core.account.entity.WeChatAccount;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeChatAccountMapper{

    /**
     * 保存记录
     */
    void save(WeChatAccount account);

    /**
     * 更新记录
     */
    void update(WeChatAccount account);

    /**
     * 根据条件查询单条记录
     */
    WeChatAccount getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<WeChatAccount> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
