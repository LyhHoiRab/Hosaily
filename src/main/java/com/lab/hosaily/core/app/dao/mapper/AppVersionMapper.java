package com.lab.hosaily.core.app.dao.mapper;




import com.lab.hosaily.core.app.entity.AppVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.doraemon.utils.mybatis.Criteria;

import java.util.List;

@Repository
public interface AppVersionMapper {

    void save(AppVersion appVersion);

    void update(AppVersion appVersion);

    AppVersion get(@Param("params") Criteria criteria);

    List<AppVersion> find(@Param("params") Criteria criteria, @Param("mixSearch") String mixSearch);

    Long count(@Param("params") Criteria criteria);

}
