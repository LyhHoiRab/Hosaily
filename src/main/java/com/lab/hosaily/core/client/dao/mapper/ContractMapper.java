package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.Contract;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractMapper{

    void save(Contract contract);

    Contract getByParams(@Param("params") Criteria criteria);

    List<Contract> findByParams(@Param("params") Criteria criteria);

    Long countByParams(@Param("params") Criteria criteria);
}
