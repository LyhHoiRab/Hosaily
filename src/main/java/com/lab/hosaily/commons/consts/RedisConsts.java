package com.lab.hosaily.commons.consts;

public class RedisConsts{

    //用户信息缓存时间
    public static final Integer USER_EFFECTIVE_SECOND = SessionConsts.EFFECTIVE_SECOND;

    //用户信息缓存时间
    public static final Integer APP_USER_EFFECTIVE_SECOND = SessionConsts.APP_EFFECTIVE_SECOND;

    //验证码缓存时间
    public static final Integer CAPTCHA_EFFECTIVE_SECOND = 300;

    //用户信息缓存前缀
    public static final String USER_INFO = "user:info:";

    //用户等级缓存
    public static final String USER_LEVEL = "user:level:";

    //AccessToken
    public static final String WECHAT_ACCESS_TOKEN = "wechat:accessToken:";

    //网站资源目录缓存
    public static final String WEB_RESOURCE = "web:resource";

    //手机验证码缓存前缀
    public static final String CAPTCHA_PHONE = "captcha:phone:";
}
