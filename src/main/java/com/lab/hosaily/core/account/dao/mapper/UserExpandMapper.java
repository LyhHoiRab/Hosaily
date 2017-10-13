package com.lab.hosaily.core.account.dao.mapper;

import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.core.account.entity.UserExpand;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExpandMapper{

    /**
     * 保存记录
     */
    void save(UserExpand userExpand);

    /**
     * 更新记录
     */
    void update(UserExpand userExpand);

    /**
     * 删除记录
     */
    void delete(String id);

    /**
     * 根据条件查询记录
     */
    UserExpand getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<UserExpand> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
