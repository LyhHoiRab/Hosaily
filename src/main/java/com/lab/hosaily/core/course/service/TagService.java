package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Tag;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.List;

public interface TagService{

    /**
     * 保存记录
     */
    void save(Tag tag);

    /**
     * 更新记录
     */
    void update(Tag tag);

    /**
     * 根据ID查询记录
     */
    Tag getById(String id);


    /**
     * 分页查询
     */
    Page<Tag> page(PageRequest pageRequest, UsingState state, String name, String organizationId, String organizationToken);

    /**
     * 列表
     */
    List<Tag> list(UsingState state, String name, String organizationId, String organizationToken);
}
