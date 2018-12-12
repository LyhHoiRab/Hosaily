package com.lab.hosaily.core.popularize.dao.mapper;

import com.lab.hosaily.core.popularize.entity.Wechat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.wah.doraemon.utils.mybatis.Criteria;

import java.util.List;

@Repository
public interface WechatMapper{

    void save(Wechat wechat);

    void update(Wechat wechat);

    Wechat get(@Param("params") Criteria criteria);

    List<Wechat> find(@Param("params") Criteria criteria);

    Long count(@Param("params") Criteria criteria);
}
