package com.lab.hosaily.core.im.dao.mapper;

import com.lab.hosaily.core.im.entity.IMUser;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface IMUserMapper{

    void save(IMUser user);

    void update(IMUser user);

    IMUser get(@Param("params") Criteria criteria);

    List<IMUser> find(@Param("params") Criteria criteria);

    Long count(@Param("params") Criteria criteria);
}
