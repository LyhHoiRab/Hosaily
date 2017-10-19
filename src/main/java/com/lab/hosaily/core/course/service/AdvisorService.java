package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.Advisor;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface AdvisorService{

    /**
     * 保存记录
     */
    void save(Advisor advisor);

    /**
     * 更新记录
     */
    void update(Advisor advisor);

    /**
     * 根据ID查询记录
     */
    Advisor getById(String id);

    /**
     * 根据状态查询
     */
    List<Advisor> findByState(UsingState state);

    /**
     * 分页查询
     */
    Page<Advisor> page(PageRequest pageRequest);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);

    /**
     * H5分页查询
     */
    Page<Advisor> pageByH5(PageRequest pageRequest);
}