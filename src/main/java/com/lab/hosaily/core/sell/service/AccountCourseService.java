package com.lab.hosaily.core.sell.service;

import com.lab.hosaily.core.sell.entity.AccountCourse;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;

import java.util.List;

public interface AccountCourseService {

    /**
     * 保存
     */
    void save(AccountCourse accountCourse);

    /**
     * 更新
     */
    void update(AccountCourse accountCourse);

    /**
     * 删除
     */
    void delete(String id);

    /**
     * 分页查询
     */
    Page<AccountCourse> page(PageRequest pageRequest, String accountId);
}
