package com.lab.hosaily.core.organization.service;

import com.lab.hosaily.core.organization.entity.Organization;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.List;

public interface OrganizationService{

    /**
     * 保存
     */
    void save(Organization organization);

    /**
     * 更新
     */
    void update(Organization organization);

    /**
     * 根据ID查询
     */
    Organization getById(String id);

    /**
     * 分页
     */
    Page<Organization> page(PageRequest pageRequest, String name, String token, UsingState state);

    /**
     * 列表
     */
    List<Organization> list(String name, String token, UsingState state);
}
