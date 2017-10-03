package com.lab.hosaily.commons.consts;

import java.net.URLEncoder;

/**
 * 网页授权业务常量
 */
public class AuthorizationConsts{

    //默认跳转路径
    public final static String DEFAULT_REDIRECT = "/page/index";

    //网页授权登录接口
    public final static String AUTHORIZE_API = "";

    //节点名称
    public final static String NODE_APPID = "appid";
    public final static String NODE_STATE = "state";
    public final static String NODE_REDIRECT_URL = "redirect_uri";

    //URL encoding加密后的授权API路径
    public static String getAuthorizeApi() throws Exception{
        return URLEncoder.encode(AUTHORIZE_API, "UTF-8");
    }
}
