package com.lab.hosaily.core.app.dao.mapper;

import com.lab.hosaily.core.app.entity.SellerClientRelation;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerClientRelationMapper {

    /**
     * 保存记录
     */
    void save(SellerClientRelation sellerClientRelation);

    /**
     * 更新记录
     */
    void update(SellerClientRelation sellerClientRelation);

    /**
     * 根据条件查询单条记录
     */
    SellerClientRelation getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<SellerClientRelation> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    Long countByParams(@Param("params") Criteria criteria);
}
