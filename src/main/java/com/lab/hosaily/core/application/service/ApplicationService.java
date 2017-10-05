package com.lab.hosaily.core.application.service;

import com.lab.hosaily.core.application.entity.Application;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;

import java.util.Map;

public interface ApplicationService{

    /**
     * 保存应用
     */
    void save(Application application);

    /**
     * 更新应用
     */
    void update(Application application);

    /**
     * 查询生成二维码参数
     */
    Map<String, Object> getQRParams(String sessionId, String token, String redirectUrl);

    /**
     * 分页查询
     */
    Page<Application> page(PageRequest pageRequest);

    /**
     * 根据ID查询
     */
    Application getById(String id);
}
