package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Level;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

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
     * 根据状态查询
     */
    List<Level> findByState(UsingState state);

    /**
     * 分页查询
     */
    Page<Level> page(PageRequest pageRequest, UsingState state, Date createTime, Date minCreateTime, Date maxCreateTime);
}
