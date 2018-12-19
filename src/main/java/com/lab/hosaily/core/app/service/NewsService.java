package com.lab.hosaily.core.app.service;

import com.lab.hosaily.core.app.entity.News;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface NewsService {

    /**
     * 保存记录
     */
    void save(News news);

    /**
     * 更新记录
     */
    void update(News news);

    /**
     * 根据ID查询记录
     */
    News getById(String id);

    /**
     * 分页查询
     */
    Page<News> page(PageRequest pageRequest, String profileId);

    /**
     * 查询列表
     */
    List<News> list(String nickname, String name, UsingState state, String organizationId, String organizationToken);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
