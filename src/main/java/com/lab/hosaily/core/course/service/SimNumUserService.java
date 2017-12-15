package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.SimNumUser;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;

public interface SimNumUserService {

    /**
     * 保存记录
     */
    void save(SimNumUser simNumUser);

    /**
     * 更新记录
     */
    void update(SimNumUser simNumUser);

    /**
     * 根据ID查询记录
     */
    SimNumUser getBySim(String sim);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 分页查询
     */
    Page<SimNumUser> page(PageRequest pageRequest, String num, String userType, String organizationId);
}
