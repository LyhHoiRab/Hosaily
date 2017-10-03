package com.lab.hosaily.commons.utils;

import com.lab.hosaily.commons.response.wechat.AccessTokenResponse;
import com.lab.hosaily.commons.response.wechat.UserInfoResponse;
import com.rab.babylon.commons.security.exception.HttpClientException;
import com.rab.babylon.commons.utils.HttpClientUtils;
import com.rab.babylon.commons.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class WeChatUtils{

    private final static String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private final static String REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    private final static String GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";

    private WeChatUtils(){

    }

    /**
     * 根据code,appid,secret获取access_token
     */
    public static AccessTokenResponse getAccessToken(String code, String appId, String secret) throws Exception{
        if(StringUtils.isBlank(code)){
            throw new IllegalArgumentException("CODE不能为空");
        }
        if(StringUtils.isBlank(appId)){
            throw new IllegalArgumentException("应用appId不能为空");
        }
        if(StringUtils.isBlank(secret)){
            throw new IllegalArgumentException("应用secret不能为空");
        }

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        CloseableHttpClient client = null;
        HttpGet get = null;

        try{
            client = HttpClientUtils.createHttpClient();
            get = HttpClientUtils.getGet(GET_ACCESS_TOKEN, params);

            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String json = EntityUtils.toString(response.getEntity());

                if(!StringUtils.isBlank(json)){
                    return ObjectUtils.deserialize(json, AccessTokenResponse.class);
                }

                return null;
            }

            throw new HttpClientException(response.getEntity().toString());
        }finally{
            client.close();
        }
    }

    /**
     * 刷新access_token
     */
    public static AccessTokenResponse refreshAccessToken(String appId, String refreshToken) throws Exception{
        if(StringUtils.isBlank(appId)){
            throw new IllegalArgumentException("应用appId不能为空");
        }
        if(StringUtils.isBlank(refreshToken)){
            throw new IllegalArgumentException("refreshToken不能为空");
        }

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appid", appId);
        params.put("grant_type", "refresh_token");
        params.put("refresh_token", refreshToken);

        CloseableHttpClient client = null;
        HttpGet get = null;

        try{
            client = HttpClientUtils.createHttpClient();
            get = HttpClientUtils.getGet(REFRESH_TOKEN, params);

            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String json = EntityUtils.toString(response.getEntity());

                if(!StringUtils.isBlank(json)){
                    return ObjectUtils.deserialize(json, AccessTokenResponse.class);
                }

                return null;
            }

            throw new HttpClientException(response.getEntity().toString());
        }finally{
            client.close();
        }
    }

    /**
     * 查询用户信息
     */
    public static UserInfoResponse getUserInfo(String accessToken, String openId) throws Exception{
        if(StringUtils.isBlank(accessToken)){
            throw new IllegalArgumentException("AccessToken不能为空");
        }
        if(StringUtils.isBlank(openId)){
            throw new IllegalArgumentException("openId不能为空");
        }

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_token", accessToken);
        params.put("openid", openId);
        params.put("lang", "zh_CN");

        CloseableHttpClient client = null;
        HttpGet get = null;

        try{
            client = HttpClientUtils.createHttpClient();
            get = HttpClientUtils.getGet(GET_USER_INFO, params);

            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String json = EntityUtils.toString(response.getEntity());

                if(!StringUtils.isBlank(json)){
                    return ObjectUtils.deserialize(json, UserInfoResponse.class);
                }

                return null;
            }

            throw new HttpClientException(response.getEntity().toString());
        }finally{
            client.close();
        }
    }
}
