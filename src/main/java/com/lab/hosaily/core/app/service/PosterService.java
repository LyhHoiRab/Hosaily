package com.lab.hosaily.core.app.service;

import com.lab.hosaily.core.app.entity.Poster;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface PosterService {

    /**
     * 保存记录
     */
    void save(Poster poster);

    /**
     * 更新记录
     */
    void update(Poster poster);

    /**
     * 根据ID查询记录
     */
    Poster getBySellerIdAndAdvisorId(String sellerId, String AdvisorId);

    Poster getBySellerId(String sellerId);

    /**
     * 分页查询
     */
    Page<Poster> page(PageRequest pageRequest, String profileId);

    /**
     * 查询列表
     */
    List<Poster> list(String nickname, String name, UsingState state, String organizationId, String organizationToken);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
