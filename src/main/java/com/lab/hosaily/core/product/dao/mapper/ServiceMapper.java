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
    void save(@Param("services") List<Service> services);

    /**
     * 删除
     */
    void deleteByMasterId(String masterId);

    /**
     * 删除
     */
    void delete(String id);
}
