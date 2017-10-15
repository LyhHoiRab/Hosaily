package com.lab.hosaily.core.page.consts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonsPathConsts{

    public static String JS_PATH;
    public static String CSS_PATH;
    public static String IMG_PATH;

    @Value(value = "${url.commons.js}")
    public void setJsPath(String jsPath){
        if(StringUtils.isBlank(JS_PATH)){
            JS_PATH = jsPath;
        }
    }

    public String getJsPath(){
        return JS_PATH;
    }

    @Value(value = "${url.commons.css}")
    public void setCssPath(String cssPath){
        if(StringUtils.isBlank(CSS_PATH)){
            CSS_PATH = cssPath;
        }
    }

    public String getCssPath(){
        return CSS_PATH;
    }

    @Value(value = "${url.commons.img}")
    public void setImgPath(String imgPath){
        if(StringUtils.isBlank(IMG_PATH)){
            IMG_PATH = imgPath;
        }
    }

    public String getImgPath(){
        return IMG_PATH;
    }
}
