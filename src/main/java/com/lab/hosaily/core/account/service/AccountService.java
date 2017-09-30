package com.lab.hosaily.core.account.service;

import com.rab.babylon.core.account.entity.Account;

public interface AccountService{

    /**
     * 小程序注册
     */
    void registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv);

    /**
     * 网站应用注册
     */
    void registerByWeb(String code);

    /**
     * 公众账号注册
     */
    Account registerByWeChat(Account account);
}
