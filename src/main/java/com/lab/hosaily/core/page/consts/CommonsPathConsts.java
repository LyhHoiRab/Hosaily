package com.lab.hosaily.core.page.consts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonsPathConsts{

    public static final String JS_PATH = "/js";
    public static final String CSS_PATH = "/css";
    public static final String IMG_PATH = "/img";
    public static final String JS_MOBILE_PATH = "/mobile/js";
    public static final String CSS_MOBILE_PATH = "/mobile/css";
    public static final String IMG_MOBILE_PATH = "/mobile/img";

    public static final String WEB_DIR = "web/";
    public static final String MOBILE_DIR = "mobile/";

    public static String H5_INDEX;
    public static String DEFAULT_REDIRECT;
    public static String AUTHORIZE_API;

    public String getAuthorizeApi(){
        return AUTHORIZE_API;
    }

    @Value("${url.h5.authorize_api}")
    public void setAuthorizeApi(String authorizeApi){
        AUTHORIZE_API = authorizeApi;
    }

    public String getDefaultRedirect(){
        return DEFAULT_REDIRECT;
    }

    @Value("${url.h5.default_redirect}")
    public void setDefaultRedirect(String defaultRedirect){
        DEFAULT_REDIRECT = defaultRedirect;
    }

    public String getH5Index(){
        return H5_INDEX;
    }

    @Value("${url.h5.index}")
    public void setH5Index(String h5Index){
        H5_INDEX = h5Index;
    }
}
