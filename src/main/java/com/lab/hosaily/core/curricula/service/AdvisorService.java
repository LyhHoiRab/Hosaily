package com.lab.hosaily.core.curricula.service;

import com.lab.hosaily.core.curricula.entity.Advisor;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

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

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
