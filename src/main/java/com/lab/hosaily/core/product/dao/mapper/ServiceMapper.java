package com.lab.hosaily.core.product.dao.mapper;

import com.lab.hosaily.core.product.entity.Service;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceMapper{

    /**
     * 保存
     */
    void save(Service service);

    /**
     * 更新
     */
    void update(Service service);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 查询
     */
    Service getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<Service> findByParams(@Param("params") Criteria criteria);
}
