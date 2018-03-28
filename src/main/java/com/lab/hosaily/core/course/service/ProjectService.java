package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.AccountProject;
import com.lab.hosaily.core.course.entity.Project;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProjectService {

    /**
     * 保存记录
     */
    void save(Project project);

    /**
     * 更新记录
     */
    void update(Project project);

    /**
     * 根据ID查询记录
     */
    Project getById(String id);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 分页查询
     */
    Page<Project> page(PageRequest pageRequest, String num, String title, String organizationId, String status, String accountId);

    /**
     * 查询列表
     */
    List<Project> list(String nickname, String name, UsingState state, String organizationId, String organizationToken);


    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);

    void summitResult(AccountProject accountProject);

}
