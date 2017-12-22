package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.consts.PurchaseState;
import com.lab.hosaily.core.client.entity.Purchase;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.List;

public interface PurchaseService{

    /**
     * 保存
     */
    void save(Purchase purchase);

    /**
     * 更新
     */
    void update(Purchase purchase);

    /**
     * 根据ID查询
     */
    Purchase getById(String id);

    /**
     * 查询
     */
   List<Purchase> list(PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price);

    /**
     * 分页
     */
    Page<Purchase> page(PageRequest pageRequest, PurchaseState purchaseState, UsingState state, String accountId, String organizationId, Double price);
}
