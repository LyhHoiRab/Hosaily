package com.lab.hosaily.core.curricula.service;

import com.lab.hosaily.core.curricula.entity.Media;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface MediaService{

    /**
     * 保存记录
     */
    void save(Media media);

    /**
     * 更新记录
     */
    void update(Media media);

    /**
     * 根据ID查询记录
     */
    Media getById(String id);

    /**
     * 根据MD5查询记录
     */
    Media getByMd5(String md5);

    /**
     * 分页查询
     */
    Page<Media> page(PageRequest pageRequest);

    /**
     * 上传媒体文件
     */
    Media upload(String path, String url, CommonsMultipartFile file);
}
