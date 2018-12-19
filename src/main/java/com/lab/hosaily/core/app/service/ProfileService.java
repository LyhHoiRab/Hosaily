package com.lab.hosaily.core.app.service;

import com.lab.hosaily.core.app.entity.Profile;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProfileService {

    /**
     * 保存记录
     */
    void save(Profile profile);

    /**
     * 更新记录
     */
    void update(Profile profile);

    /**
     * 根据ID查询记录
     */
    Profile getById(String id);

    Profile getByAccountId(String accountId);

    /**
     * 分页查询
     */
    Page<Profile> page(PageRequest pageRequest, Integer signAgreement, Integer signProfile, Integer uploaded, String name, String sellerId);

    Page<Profile> findClientsPage(PageRequest pageRequest, String clientName, String advisorId);

    /**
     * 查询列表
     */
    List<Profile> list(String advisorName, Integer role);

    /**
     * 上传图片
     */
    String upload(CommonsMultipartFile file);
}
