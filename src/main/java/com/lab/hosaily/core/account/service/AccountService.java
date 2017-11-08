package com.lab.hosaily.core.account.service;

import com.rab.babylon.core.account.entity.User;

public interface AccountService{

    /**
     * 小程序注册
     */
    User registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv);

    /**
     * 网站应用注册
     */
    User registerByWeb(String token, String code);

    /**
     * 小程序获取用户电话
     */
    String phoneByXcx(String token, String accountId, String sessionKey, String encryptedData, String iv);

    /**
     * 授权查询账户ID
     */
    String getAccountIdByAuth(String code, String token);
}
