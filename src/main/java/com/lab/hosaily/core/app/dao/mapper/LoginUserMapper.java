package com.lab.hosaily.core.app.dao.mapper;



import com.lab.hosaily.core.app.entity.LoginUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.doraemon.utils.mybatis.Criteria;

import java.util.List;

@Repository
public interface LoginUserMapper {

    void save(LoginUser loginUser);

    void update(LoginUser loginUser);

    LoginUser get(@Param("params") Criteria criteria);

    List<LoginUser> find(@Param("params") Criteria criteria, @Param("mixSearch") String mixSearch);

    Long count(@Param("params") Criteria criteria);

}
