package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

public interface PostService{

    /**
     * 保存
     */
    void save(Course post);

    /**
     * 更新
     */
    void update(Course post);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据ID查询
     */
    Course getById(String id);

    /**
     * 分页查询
     */
    Page<Course> page(PageRequest pageRequest, String advisor, UsingState state, String advisorId, String organizationId, String organizationToken, String tagName);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
