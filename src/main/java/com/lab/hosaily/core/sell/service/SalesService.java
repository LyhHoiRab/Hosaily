package com.lab.hosaily.core.sell.service;

import com.lab.hosaily.core.course.entity.Advisor;

public interface SalesService{

    /**
     * 微信号验证导师
     */
    Advisor verifyAdvisor(String wechat);
}
