package com.lab.hosaily.core.account.service;

public interface AccountService{

    /**
     * 小程序注册
     */
    void registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv);

    /**
     * 网站应用注册
     */
    void registerByWeb(String token, String code);

    /**
     * 公众账号注册
     */
    void registerByWeChat(String weChat);
}
