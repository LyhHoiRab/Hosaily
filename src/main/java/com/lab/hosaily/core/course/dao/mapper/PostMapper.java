package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.entity.Media;
import com.lab.hosaily.core.course.entity.Tag;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper{

    /**
     * 保存
     */
    void save(Course course);

    /**
     * 更新
     */
    void update(Course course);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 添加媒体
     */
    void addMedia(@Param("courseId") String courseId, @Param("medias") List<Media> medias);

    /**
     * 删除媒体
     */
    void deleteMedia(@Param("courseId") String courseId);

    /**
     * 添加子课程
     */
    void addCourse(@Param("parentId") String parentId, @Param("children") List<Course> children);

    /**
     * 删除子课程
     */
    void deleteCourse(@Param("parentId") String parentId);

    /**
     * 添加标签
     */
    void addTag(@Param("courseId") String courseId, @Param("tags") List<Tag> tags);

    /**
     * 删除标签
     */
    void deleteTag(@Param("courseId") String courseId);

    /**
     * 根据条件查询记录
     */
    Course getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<String> findIdByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<Course> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
