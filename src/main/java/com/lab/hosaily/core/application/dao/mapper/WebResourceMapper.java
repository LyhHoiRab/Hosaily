package com.lab.hosaily.core.application.dao.mapper;

import com.lab.hosaily.core.application.entity.WebResource;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebResourceMapper{

    /**
     * 保存
     */
    void save(WebResource resource);

    /**
     * 更新
     */
    void update(WebResource resource);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 查询
     */
    WebResource getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<WebResource> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
