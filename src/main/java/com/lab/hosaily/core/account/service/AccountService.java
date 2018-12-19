package com.lab.hosaily.core.account.service;

import com.lab.hosaily.core.account.entity.AppAccount;
import com.rab.babylon.core.account.entity.Account;
import com.rab.babylon.core.account.entity.User;

public interface AccountService{

    /**
     * 小程序注册
     */
    User registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv);

    /**
     * 小程序注册
     */
    User loginWeb(String phone, String password, String accountId);

    /**
     * 网站应用注册
     */
    User registerByNewWeb(String token, String code);


    /**
     * 网站应用注册
     */
    User registerByApp(AppAccount appAccount);


    /**
     * 网站应用注册
     */
    User registerByWeb(String token, String code);

    /**
     * 小程序获取用户电话
     */
    String phoneByXcx(String token, String accountId, String sessionKey, String encryptedData, String iv);

    /**
     * 小程序获取用户地理位置
     */
    void locationByXcx(String token, String accountId, Double latitude, Double longitude);

    /**
     * 授权查询账户ID
     */
    String getAccountIdByAuth(String code, String token);

    Account getById(String accountId);

    Long countByPhone(String phone);

    void saveOrUpdate(Account account);
}
