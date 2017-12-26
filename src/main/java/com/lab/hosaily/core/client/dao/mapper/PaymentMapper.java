package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.Payment;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMapper{

    /**
     * 保存
     */
    void save(Payment payment);

    /**
     * 更新
     */
    void update(Payment payment);

    /**
     * 查询
     */
    Payment getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<Payment> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);

    /**
     * 查询金额
     */
    Long priceByPurchaseId(@Param("params") Criteria criteria);
}
