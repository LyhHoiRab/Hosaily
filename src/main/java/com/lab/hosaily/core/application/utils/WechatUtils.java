package com.lab.hosaily.core.application.utils;

import com.lab.hosaily.core.application.utils.response.WechatAccessTokenResponse;
import com.rab.babylon.commons.security.exception.HttpClientException;
import com.rab.babylon.commons.utils.HttpClientUtils;
import com.rab.babylon.commons.utils.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众账号工具
 */
public class WechatUtils{

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final Charset API_CHARSET = Charset.forName("ISO-8859-1");
    private static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 查询access_token
     */
    public static WechatAccessTokenResponse getAccessToken(String appId, String secret) throws Exception{
        if(StringUtils.isBlank(appId)){
            throw new IllegalArgumentException("appId不能为空");
        }
        if(StringUtils.isBlank(secret)){
            throw new IllegalArgumentException("secret不能为空");
        }

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "client_credential");
        params.put("appid", appId);
        params.put("secret", secret);

        CloseableHttpClient client = null;

        try{
            client = HttpClientUtils.createHttpsClient();
            HttpGet get = HttpClientUtils.getGet(GET_ACCESS_TOKEN, params);

            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String json = EntityUtils.toString(response.getEntity());

                if(!StringUtils.isBlank(json)){
                    json = new String(json.getBytes(API_CHARSET), CHARSET);

                    WechatAccessTokenResponse accessToken = ObjectUtils.deserialize(json, WechatAccessTokenResponse.class);

                    if(accessToken != null){
                        return accessToken;
                    }
                }
            }

            throw new HttpClientException("accessToken查询失败");
        }catch(Exception e){
            throw new HttpClientException(e.getMessage(), e);
        }finally{
            HttpClientUtils.close(client);
        }
    }
}
