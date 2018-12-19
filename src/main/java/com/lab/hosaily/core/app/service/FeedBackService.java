package com.lab.hosaily.core.app.service;

import com.lab.hosaily.core.app.entity.FeedBack;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface FeedBackService {

    /**
     * 保存记录
     */
    void save(FeedBack feedBack);

    /**
     * 更新记录
     */
    void update(FeedBack feedBack);

    /**
     * 根据ID查询记录
     */
    FeedBack getById(String id);

    /**
     * 分页查询
     */
    Page<FeedBack> page(PageRequest pageRequest, String customerId);

    /**
     * 查询列表
     */
    List<FeedBack> list(String nickname, String name, UsingState state, String organizationId, String organizationToken);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
