package com.lab.hosaily.core.account.dao.mapper;

import com.lab.hosaily.core.account.entity.Attention;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttentionMapper{

    /**
     * 保存记录
     */
    void save(Attention attention);

    /**
     * 删除记录
     */
    void delete(String id);

    /**
     * 根据条件查询记录
     */
    List<Attention> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件判断记录是否存在
     */
    Boolean existByParams(@Param("params") Criteria criteria);
}
