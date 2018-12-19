package com.lab.hosaily.core.im.service;

import com.lab.hosaily.core.im.entity.IMUser;

import java.util.Map;

public interface IMUserService{



    /**
     * 微信APP登录
     */
    Map<String, Object> getByAccount(String account, String headImgUrl, String wxid, String nickname, String jpush, String versionName);
}
