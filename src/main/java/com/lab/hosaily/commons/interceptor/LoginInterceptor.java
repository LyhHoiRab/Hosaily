package com.lab.hosaily.commons.interceptor;

import com.lab.hosaily.commons.consts.SessionConsts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor extends HandlerInterceptorAdapter{

    private static final AntPathMatcher matcher = new AntPathMatcher();
    private static String redirectUrl;
    private List<String> excludes;

    public static AntPathMatcher getMatcher(){
        return matcher;
    }

    public List<String> getExcludes(){
        return excludes;
    }

    public void setExcludes(List<String> excludes){
        this.excludes = excludes;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String url = request.getRequestURI().substring(request.getContextPath().length());

        if(excludes != null && !excludes.isEmpty()){
            for(String exclude : excludes){
                if(matcher.match(exclude, url)){
                    return true;
                }
            }
        }

        String accountId = (String) request.getSession().getAttribute(SessionConsts.ACCOUNT_ID);

        //没有登录信息
        if(StringUtils.isBlank(accountId)){
            //重定向到首页
            response.sendRedirect("/page/h5/index");
            return false;
        }

        return true;
    }
}
