package com.lab.hosaily.core.client.dao.mapper;

import com.lab.hosaily.core.client.entity.Appointment;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentMapper{

    /**
     * 保存
     */
    void save(Appointment appointment);

    /**
     * 更新
     */
    void update(Appointment appointment);

    /**
     * 查询
     */
    Appointment getByParams(@Param("params") Criteria criteria);

    /**
     * 查询
     */
    List<Appointment> findByParams(@Param("params") Criteria criteria);

    /**
     * 查询数量
     */
    Long countByParams(@Param("params") Criteria criteria);
}
