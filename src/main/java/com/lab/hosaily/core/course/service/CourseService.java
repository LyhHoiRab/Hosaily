package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface CourseService{

    /**
     * 分页查询帖子记录
     */
    Page<Course> pageByPost(PageRequest pageRequest);

    /**
     * 分页查询课程记录
     */
    Page<Course> pageByCourse(PageRequest pageRequest);

    /**
     * H5分页查询课程记录
     */
    Page<Course> pageByH5AndCourse(PageRequest pageRequest);

    /**
     * H5分页查询帖子记录
     */
    Page<Course> pageByH5AndPost(PageRequest pageRequest);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
