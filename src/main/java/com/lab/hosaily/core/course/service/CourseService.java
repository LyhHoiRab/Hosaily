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
     * 分页查询课程记录
     */
    Page<Course> pageByCourse(PageRequest pageRequest);

    /**
     * 分页查询帖子记录
     */
    Page<Course> pageByPost(PageRequest pageRequest);

    /**
     * 根据ID查询帖子
     */
    Course getPostById(String id);

    /**
     * 根据ID查询课程
     */
    Course getCourseById(String id);

    /**
     * 根据ID查询章节
     */
    Course getChapterById(String id);

    /**
     * 根据ID查询课时
     */
    Course getSectionById(String id);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
