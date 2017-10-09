package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface CourseService{

    /**
     * 保存记录
     */
    void save(Course course);

    /**
     * 更新记录
     */
    void update(Course course);

    /**
     * 根据ID查询
     */
    Course getById(String id);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);

    /**
     * 分页查询
     */
    Page<Course> page(PageRequest pageRequest);
}
