package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.List;

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
     * 删除记录
     */
    void delete(String id);

    /**
     * 查询课程列表
     */
    List<Course> listByCourse(String tagName, String advisor, UsingState state, String accountId, String organizationId, String organizationToken);

    /**
     * 分页查询课程
     */
    Page<Course> pageByCourse(PageRequest pageRequest, String tagName, String advisor, UsingState state, String accountId, String organizationId, String organizationToken);

    /**
     * 分页查询章节
     */
    Page<Course> pageByChapter(PageRequest pageRequest, String parentId, UsingState state);

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

    /**
     * 根据账户ID和企业ID查询
     */
    List<Course> findByAccountIdAndOrganizationToken(String accountId, String organizationToken);
}
