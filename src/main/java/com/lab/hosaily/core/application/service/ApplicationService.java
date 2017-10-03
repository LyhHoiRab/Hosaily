package com.lab.hosaily.core.application.service;

import java.util.Map;

public interface ApplicationService{

    /**
     * 查询生成二维码参数
     */
    Map<String, Object> getQRParams(String sessionId, String token, String redirectUrl);
}
