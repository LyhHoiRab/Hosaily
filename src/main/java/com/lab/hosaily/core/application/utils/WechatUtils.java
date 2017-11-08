package com.lab.hosaily.core.application.utils;

import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.commons.response.wechat.AccessTokenResponse;
import com.lab.hosaily.core.account.entity.WeChatAccount;
import com.lab.hosaily.core.application.utils.response.WechatAccessTokenResponse;
import com.lab.hosaily.core.application.utils.response.WechatUserInfoResponse;
import com.rab.babylon.commons.security.exception.HttpClientException;
import com.rab.babylon.commons.utils.HttpClientUtils;
import com.rab.babylon.commons.utils.ObjectUtils;
import com.rab.babylon.commons.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.ShardedJedisPool;

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
    private static final String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info";
    private static final String AUTH_USER_INFO = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 查询access_token
     */
    private static WechatAccessTokenResponse getAccessToken(String appId, String secret) throws Exception{
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

                    if(accessToken == null || StringUtils.isBlank(accessToken.getAccessToken())){
                        accessToken = getAccessToken(appId, secret);
                    }

                    return accessToken;
                }
            }

            throw new HttpClientException("accessToken查询失败");
        }catch(Exception e){
            throw new HttpClientException(e.getMessage(), e);
        }finally{
            HttpClientUtils.close(client);
        }
    }

    /**
     * 查询access_token
     */
    public static WechatAccessTokenResponse getAccessToken(ShardedJedisPool pool, String appId, String secret, Boolean refresh) throws Exception{
        if(pool == null){
            throw new IllegalArgumentException("Redis连接池不能为空");
        }
        if(StringUtils.isBlank(appId)){
            throw new IllegalArgumentException("appId不能为空");
        }
        if(StringUtils.isBlank(secret)){
            throw new IllegalArgumentException("secret不能为空");
        }

        WechatAccessTokenResponse token = null;
        //缓存名
        String cacheName = RedisConsts.WECHAT_ACCESS_TOKEN + appId;

        //默认不刷新Token
        if(refresh == null){
            refresh = false;
        }

        if(refresh){
            token = getAccessToken(appId, secret);
            //缓存
            RedisUtils.save(pool.getResource(), cacheName, token, token.getExpiresIn());
            return token;
        }

        //从缓存中获取
        token = RedisUtils.get(pool.getResource(), cacheName, WechatAccessTokenResponse.class);

        if(token == null || StringUtils.isBlank(token.getAccessToken())){
            token = getAccessToken(pool, appId, secret, true);
        }

        return token;
    }

    /**
     * 查询用户信息
     */
    public static WechatUserInfoResponse getUserInfo(ShardedJedisPool pool, String appId, String secret, String openId) throws Exception{
        if(pool == null){
            throw new IllegalArgumentException("Redis连接池不能为空");
        }
        if(StringUtils.isBlank(appId)){
            throw new IllegalArgumentException("appId不能为空");
        }
        if(StringUtils.isBlank(secret)){
            throw new IllegalArgumentException("secret不能为空");
        }
        if(StringUtils.isBlank(openId)){
            throw new IllegalArgumentException("用户OpenId不能为空");
        }

        WechatAccessTokenResponse token = getAccessToken(pool, appId, secret, false);

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("access_token", token.getAccessToken());
        params.put("openid", openId);
        params.put("lang", "zh_CN");

        CloseableHttpClient client = null;

        try{
            client = HttpClientUtils.createHttpsClient();
            HttpGet get = HttpClientUtils.getGet(GET_USER_INFO, params);

            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String json = EntityUtils.toString(response.getEntity());

                if(!StringUtils.isBlank(json)){
                    json = new String(json.getBytes(API_CHARSET), CHARSET);

                    WechatUserInfoResponse userInfo = ObjectUtils.deserialize(json, WechatUserInfoResponse.class);

                    if(userInfo == null || StringUtils.isBlank(userInfo.getOpenId())){
                        userInfo = getUserInfo(pool, appId, secret, openId);
                    }

                    return userInfo;
                }
            }

            throw new HttpClientException("accessToken查询失败");
        }catch(Exception e){
            throw new HttpClientException(e.getMessage(), e);
        }finally{
            HttpClientUtils.close(client);
        }
    }

    public static WeChatAccount changeToWeChatAccount(WechatUserInfoResponse userInfo){
        if(userInfo == null){
            throw new IllegalArgumentException("微信账户信息不能为空");
        }

        WeChatAccount weChatAccount = new WeChatAccount();
        weChatAccount.setUnionId(userInfo.getUnionId());
        weChatAccount.setOpenId(userInfo.getOpenId());
        weChatAccount.setSex(userInfo.getSex());
        weChatAccount.setCity(userInfo.getCity());
        weChatAccount.setCountry(userInfo.getCountry());
        weChatAccount.setProvince(userInfo.getProvince());
        weChatAccount.setHeadImgUrl(userInfo.getHeadimgurl());
        weChatAccount.setNickname(userInfo.getNickname());
        weChatAccount.setSubscribe(userInfo.getSubscribe() == 1);
        weChatAccount.setSubscribeTime(userInfo.getSubscribeTime());
        weChatAccount.setLanguage(userInfo.getLanguage());
        weChatAccount.setRemark(userInfo.getRemark());

        return weChatAccount;
    }

    /**
     * 根据授权Code查询用户openId和accessToken
     */
    public static AccessTokenResponse getAccessTokenByCode(String code, String appId, String secret) throws Exception{
        if(StringUtils.isBlank(code)){
            throw new IllegalArgumentException("授权Code不能为空");
        }
        if(StringUtils.isBlank(appId)){
            throw new IllegalArgumentException("公众号AppId不能为空");
        }
        if(StringUtils.isBlank(secret)){
            throw new IllegalArgumentException("公众号Secret不能为空");
        }

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "authorization_code   ");
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("code", code);

        CloseableHttpClient client = null;

        try{
            client = HttpClientUtils.createHttpsClient();
            HttpGet get = HttpClientUtils.getGet(AUTH_USER_INFO, params);

            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String json = EntityUtils.toString(response.getEntity());

                if (!StringUtils.isBlank(json)) {
                    json = new String(json.getBytes(API_CHARSET), CHARSET);

                    if(!StringUtils.isBlank(json)){
                        AccessTokenResponse token = ObjectUtils.deserialize(json, AccessTokenResponse.class);
                        return token;
                    }
                }

                return null;
            }

            throw new HttpClientException("accessToken查询失败");
        }catch(Exception e){
            throw new HttpClientException(e.getMessage(), e);
        }finally{
            HttpClientUtils.close(client);
        }
    }
}
