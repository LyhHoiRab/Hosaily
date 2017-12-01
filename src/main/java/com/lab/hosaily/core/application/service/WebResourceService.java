package com.lab.hosaily.core.application.service;

import com.lab.hosaily.core.application.entity.WebResource;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.List;

public interface WebResourceService{

    /**
     * 保存
     */
    void save(WebResource resource);

    /**
     * 更新
     */
    void update(WebResource resource);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 根据域名查询缓存
     */
    WebResource getCacheByDomain(String domain);

    /**
     * 更新缓存
     */
    void updateCache();

    /**
     * 根据ID查询
     */
    WebResource getById(String id);

    /**
     * 分页
     */
    Page<WebResource> page(PageRequest pageRequest, UsingState state, String domain, String imgUrl, String cssUrl, String jsUrl, String organizationId, String organizationToken);

    /**
     * 列表
     */
    List<WebResource> list(UsingState state, String domain, String imgUrl, String cssUrl, String jsUrl, String organizationId, String organizationToken);
}
