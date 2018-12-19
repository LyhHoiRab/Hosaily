package com.lab.hosaily.core.app.service;

import com.lab.hosaily.core.app.entity.Order;
import com.lab.hosaily.core.app.entity.OrderProfile;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface OrderService {

    /**
     * 保存记录
     */
    void save(Order order);

    /**
     * 更新记录
     */
    void update(Order order);

    /**
     * 根据ID查询记录
     */
    Order getByAgreementId(String agreementId);

    /**
     * 根据ID查询记录
     */
    Order getById(String id, String advisorStatus);

    List<OrderProfile> getOrderProfileById(String orderId);

    /**
     * 分页查询
     */
    Page<Order> page(PageRequest pageRequest, String sellerId, String advisorName, String assign, String mixSearch, String startTime, String endTime);

    /**
     * 查询列表
     */
    List<Order> list(String nickname, String name, UsingState state, String organizationId, String organizationToken);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);


    List<Order> countOrderByParam(String startTime, String endTime, String countType, String sellerId);
}
