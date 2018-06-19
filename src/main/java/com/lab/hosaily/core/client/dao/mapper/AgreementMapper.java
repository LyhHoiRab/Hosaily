package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.Agreement;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementMapper{

    /**
     * 保存
     */
    void save(Agreement agreement);

    /**
     * 更新
     */
    void update(Agreement agreement);

    /**
     * 查询
     */
    Agreement getByParams(@Param("params") Criteria criteria);

    List<Agreement> findByParams(@Param("params") Criteria criteria);

    Long countByParams(@Param("params") Criteria criteria);
}
