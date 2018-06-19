package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.Regulation;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegulationMapper{

    void save(Regulation regulation);

    void update(Regulation regulation);

    Regulation getByParams(@Param("params") Criteria criteria);

    List<Regulation> findByParams(@Param("params") Criteria criteria);

    Long countByParams(@Param("params") Criteria criteria);
}
