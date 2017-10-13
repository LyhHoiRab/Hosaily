package com.lab.hosaily.core.page.consts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonsPathConsts{

    public static String JS_PATH;

    public static String CSS_PATH;

    @Value(value = "${url.commons.js}")
    public void setJsPath(String jsPath){
        if(StringUtils.isBlank(JS_PATH)){
            JS_PATH = jsPath;
        }
    }

    public static String getJsPath(){
        return JS_PATH;
    }

    @Value(value = "${url.commons.css}")
    public static void setCssPath(String cssPath){
        if(StringUtils.isBlank(CSS_PATH)){
            CSS_PATH = cssPath;
        }
    }

    public static String getCssPath(){
        return CSS_PATH;
    }
}
