package com.lab.hosaily.core.page.consts;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonsPathConsts{

    public static String JS_PATH;
    public static String CSS_PATH;
    public static String IMG_PATH;
    public static String MOBILE_JS_PATH;
    public static String MOBILE_CSS_PATH;
    public static String MOBILE_IMG_PATH;
    public static String H5_INDEX;
    public static String DEFAULT_REDIRECT;
    public static String AUTHORIZE_API;
    //WEB端目录
    public static final String WEB_DIR = "web/";
    //移动端目录
    public static final String MOBILE_DIR = "mobile/";

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

    @Value(value = "${url.h5.index}")
    public void setH5Index(String index){
        if(StringUtils.isBlank(H5_INDEX)){
            H5_INDEX = index;
        }
    }

    public String getH5Index(){
        return H5_INDEX;
    }

    @Value(value = "${url.h5.default_redirect}")
    public void setDefaultRedirect(String defaultRedirect){
        if(StringUtils.isBlank(DEFAULT_REDIRECT)){
            DEFAULT_REDIRECT = defaultRedirect;
        }
    }

    public String getDefaultRedirect(){
        return DEFAULT_REDIRECT;
    }

    @Value(value = "${url.h5.authorize_api}")
    public void setAuthorizeApi(String authorizeApi){
        if(StringUtils.isBlank(AUTHORIZE_API)){
            AUTHORIZE_API = authorizeApi;
        }
    }

    public String getAuthorizeApi(){
        return AUTHORIZE_API;
    }

    public String getMobileJsPath(){
        return MOBILE_JS_PATH;
    }

    @Value(value = "${url.commons.mobile.js}")
    public void setMobileJsPath(String mobileJsPath){
        MOBILE_JS_PATH = mobileJsPath;
    }

    public String getMobileCssPath(){
        return MOBILE_CSS_PATH;
    }

    @Value(value = "${url.commons.mobile.css}")
    public void setMobileCssPath(String mobileCssPath){
        MOBILE_CSS_PATH = mobileCssPath;
    }

    public String getMobileImgPath(){
        return MOBILE_IMG_PATH;
    }

    @Value(value = "${url.commons.mobile.img}")
    public void setMobileImgPath(String mobileImgPath){
        MOBILE_IMG_PATH = mobileImgPath;
    }
}
