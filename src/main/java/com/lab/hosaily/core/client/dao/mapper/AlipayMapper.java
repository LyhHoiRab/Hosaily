package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.Alipay;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlipayMapper{

    /**
     * 保存
     */
    void save(Alipay alipay);

    /**
     * 更新
     */
    void update(Alipay alipay);

    /**
     * 查询
     */
    Alipay getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<Alipay> findByParams(@Param("params") Criteria criteria);
}
