package com.lab.hosaily.core.curricula.service;

import com.lab.hosaily.core.curricula.entity.Level;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LevelService{

    /**
     * 保存记录
     */
    void save(Level level);

    /**
     * 更新记录
     */
    void update(Level level);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);

    /**
     * 根据ID查询
     */
    Level getById(String id);

    /**
     * 分页查询
     */
    Page<Level> page(PageRequest pageRequest);
}
