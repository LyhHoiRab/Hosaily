package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.Purchase;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseMapper{

    /**
     * 保存
     */
    void save(Purchase purchase);

    /**
     * 更新
     */
    void update(Purchase purchase);

    /**
     * 查询
     */
    Purchase getByParams(@Param("params") Criteria criteria);

    /**
     * 查询ID
     */
    List<String> findIdByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<Purchase> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
