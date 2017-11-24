package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Customization;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

public interface CustomizationService{

    /**
     * 保存
     */
    void save(Customization customization);

    /**
     * 更新
     */
    void update(Customization customization);

    /**
     * 根据ID查询
     */
    Customization getById(String id);

    /**
     * 分页查询
     */
    Page<Customization> page(PageRequest pageRequest, UsingState state, String tagName, String organizationId, String organizationToken);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
