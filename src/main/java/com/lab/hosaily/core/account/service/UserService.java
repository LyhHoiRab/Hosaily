package com.lab.hosaily.core.account.service;

import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.Sex;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

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
     * 根据ID查询记录
     */
    User getByAccountId(String id);

    /**
     * 根据accountId查询用户缓存信息
     */
    User getCacheByAccountId(String accountId);

    /**
     * 分页查询
     */
    Page<User> page(PageRequest pageRequest, Sex sex, UsingState state, String wechat, String nickname, String name, Integer code, String organizationId, String organizationToke);

    /**
     * 查询列表
     */
    List<User> list(Sex sex, UsingState state, String wechat, String nickname, String name, Integer code, String organizationId, String organizationToken);
}
