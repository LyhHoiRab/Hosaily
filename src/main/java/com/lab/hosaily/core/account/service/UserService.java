package com.lab.hosaily.core.account.service;

import com.rab.babylon.core.account.entity.User;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface UserService{

    /**
     * 保存记录
     */
    void save(User user);

    /**
     * 更新记录
     */
    void update(User user);

    /**
     * 修改用户信息
     */
    void updateByH5(User user);

    /**
     * 上传头像
     */
    String upload(CommonsMultipartFile file);

    /**
     * 根据ID查询记录
     */
    User getById(String id);

    /**
     * 根据accountId查询用户缓存信息
     */
    User getCacheByAccountId(String accountId);
}
