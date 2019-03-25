package com.lab.hosaily.core.app.dao.mapper;

import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.client.consts.AgreementState;
import com.rab.babylon.commons.security.mybatis.Criteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileMapper {

    /**
     * 保存记录
     */
    void save(Profile profile);

    /**
     * 更新记录
     */
    void update(Profile profile);

    /**
     * 根据条件查询单条记录
     */
    Profile getByParams(@Param("params") Criteria criteria);

    /**
     * 根据条件查询记录
     */
    List<Profile> findByParams(@Param("params") Criteria criteria);


    List<Profile> findByMixs(@Param("signAgreement") Integer signAgreement, @Param("signProfile") Integer signProfile, @Param("uploaded") Integer uploaded, @Param("name") String name, @Param("sellerId") String sellerId, @Param("role") Integer role, @Param("beginRow") Long beginRow, @Param("pageSize") Long pageSize);





    Long countByClientsPage(@Param("clientName") String clientName, @Param("advisorId") String advisorId);
    List<Profile> findByClientsPage(@Param("clientName") String clientName, @Param("advisorId") String advisorId, @Param("beginRow") Long beginRow, @Param("pageSize") Long pageSize);




    /**
     * 根据条件查询记录
     */
    Long countByParams(@Param("params") Criteria criteria);


    Long countByMixs(@Param("signAgreement") Integer signAgreement, @Param("signProfile") Integer signProfile, @Param("uploaded") Integer uploaded, @Param("name") String name, @Param("sellerId") String sellerId, @Param("role") Integer role);





    Long countAdvisorsPage(@Param("advisorName") String advisorName, @Param("clientId") String clientId);
    List<Profile> findAdvisorsPage(@Param("advisorName") String advisorName, @Param("clientId") String clientId, @Param("beginRow") Long beginRow, @Param("pageSize") Long pageSize);

}
