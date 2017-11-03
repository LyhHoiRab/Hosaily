package com.lab.hosaily.core.sell.dao.mapper;

import com.lab.hosaily.core.sell.entity.Order;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper{

    /**
     * 保存
     */
    void save(Order order);

    /**
     * 更新
     */
    void update(Order order);

    /**
     * 删除
     * 逻辑删除
     */
    void delete(String id);

    /**
     * 根据条件查询
     */
    Order getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<Order> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询ID
     */
    List<String> findIdByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
