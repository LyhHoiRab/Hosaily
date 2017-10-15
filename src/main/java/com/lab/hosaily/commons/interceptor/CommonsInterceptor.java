package com.lab.hosaily.commons.interceptor;

import com.lab.hosaily.commons.utils.URLUtils;
import com.lab.hosaily.core.page.consts.CommonsPathConsts;
import org.springframework.ui.ModelMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CommonsInterceptor  extends HandlerInterceptorAdapter{

    private static final AntPathMatcher matcher = new AntPathMatcher();
    private List<String> includes;

    public List<String> getIncludes(){
        return includes;
    }

    public void setIncludes(List<String> includes){
        this.includes = includes;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        String url = request.getRequestURI().substring(request.getContextPath().length());

        if(includes != null && !includes.isEmpty()){
            for(String include : includes){
                if(matcher.match(include, url)){
                    String basePath = URLUtils.getBasePath(request);

                    ModelMap content = modelAndView.getModelMap();
                    content.put("commonsJsPath", CommonsPathConsts.JS_PATH);
                    content.put("commonsCssPath", CommonsPathConsts.CSS_PATH);
                    content.put("commonsImgPath", CommonsPathConsts.IMG_PATH);
                    content.put("basePath", basePath);
                }
            }
        }
    }
}
