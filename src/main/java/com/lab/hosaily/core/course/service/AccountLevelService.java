package com.lab.hosaily.core.course.service;

import com.lab.hosaily.core.course.entity.AccountLevel;

public interface AccountLevelService{

    /**
     * 根据账户ID查询
     */
    AccountLevel getByAccountId(String accountId);
}
