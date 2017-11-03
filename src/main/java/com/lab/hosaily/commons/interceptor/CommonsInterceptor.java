package com.lab.hosaily.commons.interceptor;

import com.lab.hosaily.commons.utils.URLUtils;
import com.lab.hosaily.core.page.consts.CommonsPathConsts;
import com.rab.babylon.commons.utils.UserAgentUtils;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.ui.ModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CommonsInterceptor extends HandlerInterceptorAdapter{

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        String url = request.getRequestURI().substring(request.getContextPath().length());

        String basePath = URLUtils.getBasePath(request);
        ModelMap content = modelAndView.getModelMap();

        if(matcher.match("/page/h5/**", url)){
            String userAgentInfo = request.getHeader("user-agent");
            //客户端信息
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentInfo);
            //操作系统
            DeviceType deviceType = userAgent.getOperatingSystem().getDeviceType();

            if(deviceType != null && deviceType.equals(DeviceType.COMPUTER)){
                content.put("commonsJsPath", CommonsPathConsts.JS_PATH);
                content.put("commonsCssPath", CommonsPathConsts.CSS_PATH);
                content.put("commonsImgPath", CommonsPathConsts.IMG_PATH);

                modelAndView.setViewName(CommonsPathConsts.WEB_DIR + modelAndView.getViewName());
            }else{
                content.put("commonsJsPath", CommonsPathConsts.MOBILE_JS_PATH);
                content.put("commonsCssPath", CommonsPathConsts.MOBILE_CSS_PATH);
                content.put("commonsImgPath", CommonsPathConsts.MOBILE_IMG_PATH);

                modelAndView.setViewName(CommonsPathConsts.MOBILE_DIR + modelAndView.getViewName());
            }
        }

        content.put("basePath", basePath);
    }
}
