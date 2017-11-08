package com.lab.hosaily.core.popularize.dao.mapper;

import com.lab.hosaily.core.popularize.entity.TestLibrary;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestLibraryMapper{

    /**
     * 保存
     */
    void save(TestLibrary library);

    /**
     * 更新
     */
    void update(TestLibrary library);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 查询
     */
    TestLibrary getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<TestLibrary> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
