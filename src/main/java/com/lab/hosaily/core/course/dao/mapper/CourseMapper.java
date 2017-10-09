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
     * 保存记录
     */
    void save(Course course);

    /**
     * 更新记录
     */
    void update(Course course);

    /**
     * 添加关联媒体
     */
    void addMedia(@Param("courseId") String courseId, @Param("medias") List<Media> medias);

    /**
     * 删除关联媒体
     */
    void deleteMedia(@Param("courseId") String courseId);

    /**
     * 添加关联标签
     */
    void addTag(@Param("courseId") String courseId, @Param("tags") List<Tag> tags);

    /**
     * 删除关联标签
     */
    void deleteTag(@Param("courseId") String courseId);

    /**
     * 添加关联等级
     */
    void addLevel(@Param("courseId") String courseId, @Param("levels") List<Level> levels);

    /**
     * 删除关联等级
     */
    void deleteLevel(@Param("courseId") String courseId);

    /**
     * 根据条件查询单条记录
     */
    Course getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询多条记录
     */
    List<Course> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
