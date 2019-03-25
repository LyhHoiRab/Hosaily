package com.lab.hosaily.core.app.dao.mapper;


import com.lab.hosaily.core.app.entity.InviteWeChatAccount;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteWeChatAccountMapper {

    /**
     * 保存记录
     */
    void save(InviteWeChatAccount account);

    /**
     * 更新记录
     */
    void update(InviteWeChatAccount account);

    /**
     * 根据条件查询单条记录
     */
    InviteWeChatAccount getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<InviteWeChatAccount> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
