package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.entity.Level;
import com.lab.hosaily.core.course.entity.Media;
import com.lab.hosaily.core.course.entity.Tag;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper{

    /**
     * 保存
     */
    void save(Course course);

    /**
     * 更新
     */
    void update(Course course);

    /**
     * 添加标签
     */
    void addTag(@Param("courseId") String courseId, @Param("tags") List<Tag> tags);

    /**
     * 删除标签
     */
    void deleteTag(@Param("courseId") String courseId);

    /**
     * 添加等级
     */
    void addLevel(@Param("courseId") String courseId, @Param("levels") List<Level> levels);

    /**
     * 删除等级
     */
    void deleteLevel(@Param("courseId") String courseId);

    /**
     * 添加媒体
     */
    void addMedia(@Param("courseId") String courseId, @Param("medias") List<Media> medias);

    /**
     * 删除媒体
     */
    void deleteMedia(@Param("courseId") String courseId);

    /**
     * 根据条件查询课程ID
     */
    List<String> findIdByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询课程
     */
    List<Course> findCourseByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询章节
     */
    List<Course> findChapterByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询课时
     */
    List<Course> findBySectionByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询课程
     */
    Course getCourseByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询章节
     */
    Course getChapterByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询课时
     */
    Course getSectionByParams(@Param("params") Criteria criteria);
}
