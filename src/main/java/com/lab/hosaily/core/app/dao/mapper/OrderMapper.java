package com.lab.hosaily.core.app.dao.mapper;

import com.lab.hosaily.core.app.entity.Order;
import com.lab.hosaily.core.app.entity.OrderProfile;
import com.lab.hosaily.core.app.entity.Profile;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.core.account.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderMapper {

    /**
     * 保存记录
     */
    void save(Order order);

    /**
     * 更新记录
     */
    void update(Order order);

    /**
     * 根据条件查询单条记录
     */
    Order getByParams(@Param("params") Criteria criteria);


    List<OrderProfile> getOrderProfileById(@Param("orderId") String orderId);

    /**
     * 根据条件查询记录
     */
    List<Order> findByParams(@Param("params") Criteria criteria, @Param("mixSearch") String mixSearch, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 根据条件查询记录
     */
    Long countByParams(@Param("params") Criteria criteria, @Param("mixSearch") String mixSearch);


    /**
     * 添加媒体
     */
    void addOrderProfiles(@Param("orderProfiles") List<OrderProfile> orderProfiles);
    void updateOrderProfile(OrderProfile orderProfile);

    void saveOrderProfile(OrderProfile orderProfile);
    /**
     * 删除媒体
     */
    void deleteOrderProfiles(@Param("orderId") String orderId);




    List<Order> countOrderByParam(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("countType") String countType, @Param("sellerId") String sellerId);


}
