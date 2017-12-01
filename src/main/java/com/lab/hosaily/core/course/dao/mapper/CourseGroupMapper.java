package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Course;
import com.lab.hosaily.core.course.entity.CourseGroup;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseGroupMapper{

    /**
     * 保存
     */
    void save(CourseGroup group);

    /**
     * 更新
     */
    void update(CourseGroup group);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 添加课程
     */
    void addCourse(@Param("groupId") String groupId, @Param("courses") List<Course> courses);

    /**
     * 删除课程
     */
    void deleteCourse(@Param("groupId") String groupId);

    /**
     * 添加用户
     */
    void addAccount(@Param("accountId") String accountId, @Param("groups") List<CourseGroup> groups);

    /**
     * 删除用户
     */
    void deleteAccount(@Param("accountId") String accountId);

    /**
     * 删除用户
     */
    void deleteAccountByGroupId(@Param("groupId") String groupId);

    /**
     * 查询
     */
    CourseGroup getByParams(@Param("params") Criteria criteria);

    /**
     * 查询ID
     */
    List<String> findIdByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<CourseGroup> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);

    /**
     * 是否有授权课程
     */
    Boolean hasCourse(@Param("accountId") String accountId, @Param("courseId") String courseId);

    /**
     * 账户-课程关联表
     */
    Boolean accountCourse(@Param("accountId") String accountId, @Param("courseId") String courseId);
}
