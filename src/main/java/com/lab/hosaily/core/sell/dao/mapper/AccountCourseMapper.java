package com.lab.hosaily.core.sell.dao.mapper;

import com.lab.hosaily.core.sell.entity.AccountCourse;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountCourseMapper{

    /**
     * 保存
     */
    void save(AccountCourse accountCourse);

    /**
     * 批量保存
     */
    void saveBatch(@Param("list") List<AccountCourse> accountCourses);

    /**
     * 更新
     */
    void update(AccountCourse accountCourse);

    /**
     * 根据条件查询
     */
    List<AccountCourse> findByParams(@Param("params") Criteria criteria);
}
