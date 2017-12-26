package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.entity.Agreement;

public interface AgreementService{

    /**
     * 保存
     */
    void save(Agreement agreement);

    /**
     * 更新
     */
    void update(Agreement agreement);

    /**
     * 根据ID查询
     */
    Agreement getById(String id);

    /**
     * 根据购买ID查询
     */
    Agreement getByPurchaseId(String purchaseId);

    /**
     * 协议确认
     */
    void affirm(String id);
}
