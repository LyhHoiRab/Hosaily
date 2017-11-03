package com.lab.hosaily.core.sell.service;

import com.lab.hosaily.core.sell.entity.PaymentType;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface PaymentTypeService{

    /**
     * 保存
     */
    void save(PaymentType paymentType);

    /**
     * 更新
     */
    void update(PaymentType paymentType);

    /**
     * 根据ID查询
     */
    PaymentType getById(String id);

    /**
     * 分页查询
     */
    Page<PaymentType> page(PageRequest pageRequest, UsingState state, String name);

    /**
     * 查询列表
     */
    List<PaymentType> list(UsingState state, String name);

    /**
     * 上传
     */
    String upload(CommonsMultipartFile file);
}
