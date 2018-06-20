package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Option;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface OptionService {

    /**
     * 保存记录
     */
    void save(Option option);

    /**
     * 更新记录
     */
    void update(Option option);

    /**
     * 根据ID查询记录
     */
    Option getById(String id);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 分页查询
     */
    Page<Option> page(PageRequest pageRequest, String title, String projectId, String questionId, String questionOption, String organizationId);


    /**
     * 查询列表
     */
    List<Option> list(String questionId);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);

}
