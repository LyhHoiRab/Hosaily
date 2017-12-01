package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.CourseGroup;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.List;

public interface CourseGroupService{

    /**
     * 保存
     */
    void save(CourseGroup group);

    /**
     * 更新
     */
    void update(CourseGroup group);

    /**
     * 删除课程组
     */
    void delete(String id);

    /**
     * 用户授权
     */
    void authorization(String accountId, List<CourseGroup> groups);

    /**
     * 列表
     */
    List<CourseGroup> list(String accountId, String groupId, String organizationId, String organizationToken, UsingState state, String name);

    /**
     * 分页查询
     */
    Page<CourseGroup> page(PageRequest pageRequest, String groupId, String organizationId, String organizationToken, UsingState state, String name);

    /**
     * 根据ID查询
     */
    CourseGroup getById(String id);

    /**
     * 是否有授权课程
     */
    Boolean hasCourse(String accountId, String courseId);
}
