package com.lab.hosaily.core.product.service;

import com.lab.hosaily.core.product.entity.Product;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.List;

public interface ProductService{

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
    Product getById(String id);

    /**
     * 查询
     */
    List<Product> list(UsingState state, String name, Double price, Integer duration, String organizationId);

    /**
     * 分页
     */
    Page<Product> page(PageRequest pageRequest, UsingState state, String name, Double price, Integer duration, String organizationId);
}
