package com.lab.hosaily.core.product.dao.mapper;

import com.lab.hosaily.core.product.entity.Product;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper{

    /**
     * 保存
     */
    void save(Product product);

    /**
     * 更新
     */
    void update(Product product);

    /**
     * 查询
     */
    Product getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<Product> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
