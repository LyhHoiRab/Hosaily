package com.lab.hosaily.core.course.dao.mapper;

import com.lab.hosaily.core.course.entity.Customization;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomizationMapper{

    /**
     * 保存
     */
    void save(Customization customization);

    /**
     * 更新
     */
    void update(Customization customization);

    /**
     * 根据条件查询
     */
    Customization getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询
     */
    List<Customization> findByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
