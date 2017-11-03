package com.lab.hosaily.core.sell.dao.mapper;

import com.lab.hosaily.core.sell.entity.PaymentType;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeMapper{

    /**
     * 保存
     */
    void save(PaymentType paymentType);

    /**
     * 更新
     */
    void update(PaymentType paymentType);

    /**
     * 根据条件查询
     */
    PaymentType getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<PaymentType> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
