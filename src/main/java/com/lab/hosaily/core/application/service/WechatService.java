package com.lab.hosaily.core.application.service;

import com.lab.hosaily.core.application.entity.Application;

import java.util.Map;

public interface WechatService{

    /**
     * 微信平台接入验证
     */
    boolean authorize(String token, String signature, String timestamp, String nonce);

    /**
     * 微信报文处理入口
     */
    String reply(String token, Map<String, Object> xml);

    /**
     * 微信事件处理
     */
    String event(Map<String, Object> xml, Application application);

    /**
     * 微信关注事件处理
     */
    String subscribe(Map<String, Object> xml, Application application);

    /**
     * 微信地理位置事件处理
     */
    String location(Map<String, Object> xml, Application application);
}
