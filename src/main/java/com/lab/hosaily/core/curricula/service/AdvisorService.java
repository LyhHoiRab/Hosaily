package com.lab.hosaily.core.curricula.service;

import com.lab.hosaily.core.curricula.entity.Advisor;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;

public interface AdvisorService{

    /**
     * 保存记录
     */
    void save(Advisor advisor);

    /**
     * 更新记录
     */
    void update(Advisor advisor);

    /**
     * 根据ID查询记录
     */
    Advisor getById(String id);

    /**
     * 分页查询
     */
    Page<Advisor> page(PageRequest pageRequest);
}
