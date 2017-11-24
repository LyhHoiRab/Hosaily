package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.consts.MediaType;
import com.lab.hosaily.core.course.entity.Media;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

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
    Page<Media> page(PageRequest pageRequest, String remark, MediaType type, String suffix, String organizationId, String organizationToken, UsingState state);

    /**
     * 查询列表
     */
    List<Media> list(String remark, MediaType type, String suffix, String organizationId, String organizationToken, UsingState state);

    /**
     * 上传媒体文件
     */
    Media upload(String path, String url, CommonsMultipartFile file);
}
