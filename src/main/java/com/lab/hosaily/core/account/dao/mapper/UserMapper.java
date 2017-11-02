package com.lab.hosaily.core.account.dao.mapper;

import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.core.account.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper{

    /**
     * 保存记录
     */
    void save(User user);

    /**
     * 更新记录
     */
    void update(User user);

    /**
     * 根据条件查询记录
     */
    User getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询用户ID
     */
    List<String> findIdByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<User> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
