package com.lab.hosaily.commons.interceptor;

import com.lab.hosaily.commons.utils.URLUtils;
import com.lab.hosaily.core.application.entity.WebResource;
import com.lab.hosaily.core.application.service.WebResourceService;
import com.lab.hosaily.core.page.consts.CommonsPathConsts;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonsInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private WebResourceService webResourceService;

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        String url = request.getRequestURI().substring(request.getContextPath().length());
        String basePath = URLUtils.getBasePath(request);

        ModelMap content = modelAndView.getModelMap();

        //查询域名配置
        WebResource resource = webResourceService.getCacheByDomain(basePath);

        if(matcher.match("/page/h5/**", url)){
            String userAgentInfo = request.getHeader("user-agent");
            //客户端信息
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentInfo);
            //操作系统
            DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();

            if(deviceType != null && deviceType.equals(DeviceType.COMPUTER)){
                content.put("commonsJsPath", resource == null ? basePath + CommonsPathConsts.JS_PATH : resource.getJsUrl());
                content.put("commonsCssPath", resource == null ? basePath + CommonsPathConsts.CSS_PATH : resource.getCssUrl());
                content.put("commonsImgPath", resource == null ? basePath + CommonsPathConsts.IMG_PATH : resource.getImgUrl());

                modelAndView.setViewName(CommonsPathConsts.WEB_DIR + modelAndView.getViewName());
            }else{
                content.put("commonsJsPath", resource == null ? basePath + CommonsPathConsts.JS_MOBILE_PATH : resource.getMobileJsUrl());
                content.put("commonsCssPath", resource == null ? basePath + CommonsPathConsts.CSS_MOBILE_PATH : resource.getMobileCssUrl());
                content.put("commonsImgPath", resource == null ? basePath + CommonsPathConsts.IMG_MOBILE_PATH : resource.getMobileImgUrl());

                modelAndView.setViewName(CommonsPathConsts.MOBILE_DIR + modelAndView.getViewName());
            }
        }

//        if(matcher.match("/page/h5/**", url)){
//            String userAgentInfo = request.getHeader("user-agent");
//            //客户端信息
//            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentInfo);
//            //操作系统
//            DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();
//
//            if(deviceType != null && deviceType.equals(DeviceType.COMPUTER)){
//                if(domain.startsWith(CommonsPathConsts.DOMAIN_KULIAO)){
//                    content.put("commonsJsPath", CommonsPathConsts.JS_PATH);
//                    content.put("commonsCssPath", CommonsPathConsts.CSS_PATH);
//                    content.put("commonsImgPath", CommonsPathConsts.IMG_PATH);
//                }else{
//                    content.put("commonsJsPath", CommonsPathConsts.JS_PATH_YHQS);
//                    content.put("commonsCssPath", CommonsPathConsts.CSS_PATH_YHQS);
//                    content.put("commonsImgPath", CommonsPathConsts.IMG_PATH_YHQS);
//                }
//
//                modelAndView.setViewName(CommonsPathConsts.WEB_DIR + modelAndView.getViewName());
//            }else{
//                if(domain.startsWith(CommonsPathConsts.DOMAIN_KULIAO)){
//                    content.put("commonsJsPath", CommonsPathConsts.MOBILE_JS_PATH);
//                    content.put("commonsCssPath", CommonsPathConsts.MOBILE_CSS_PATH);
//                    content.put("commonsImgPath", CommonsPathConsts.MOBILE_IMG_PATH);
//                }else{
//                    content.put("commonsJsPath", CommonsPathConsts.JS_PATH_YHQS);
//                    content.put("commonsCssPath", CommonsPathConsts.CSS_PATH_YHQS);
//                    content.put("commonsImgPath", CommonsPathConsts.IMG_PATH_YHQS);
//                }
//
//                modelAndView.setViewName(CommonsPathConsts.MOBILE_DIR + modelAndView.getViewName());
//            }
//        }

        content.put("basePath", basePath);
    }
}
