package com.lab.hosaily.core.application.service;

public interface WechatService{

    /**
     * 微信平台接入验证
     */
    boolean authorize(String token, String signature, String timestamp, String nonce);

}
