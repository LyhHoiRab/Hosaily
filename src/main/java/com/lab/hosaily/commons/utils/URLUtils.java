package com.lab.hosaily.commons.utils;

import javax.servlet.http.HttpServletRequest;

public class URLUtils{

    private URLUtils(){

    }

    /**
     * 查询项目全路径
     * 协议 + 域名 + 端口
     */
    public static String getBasePath(HttpServletRequest request){
        if(request == null){
            throw new IllegalArgumentException("请求信息不能为空");
        }

        //80端口不显示
        return request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + request.getContextPath();
    }
}
