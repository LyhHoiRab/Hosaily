package com.lab.hosaily.core.popularize.dao.mapper;

import com.lab.hosaily.core.popularize.entity.TestLogs;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestLogsMapper{

    /**
     * 保存
     */
    void save(TestLogs logs);

    /**
     * 查询
     */
    TestLogs getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<TestLogs> findByParams(@Param("params") Criteria criteria);
}
