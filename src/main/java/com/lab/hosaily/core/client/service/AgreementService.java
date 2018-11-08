package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.consts.AgreementState;
import com.lab.hosaily.core.client.entity.Agreement;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

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

    Page<Agreement> page(PageRequest pageRequest, String accountId, String serviceId, AgreementState state);

    String create(String serviceId, String productId, Double price, Integer duration);

    void fill(String id, String client, String phone, String address, String idCard, String wechat, String email, String emergencyContact, String accountId);

    void sign(String id, CommonsMultipartFile file);

    void backToEdit(String id);

    void share(String id, String accountId);

    void effectiveCheck();
}
