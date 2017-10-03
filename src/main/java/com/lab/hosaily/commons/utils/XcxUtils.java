package com.lab.hosaily.commons.utils;

import com.google.gson.reflect.TypeToken;
import com.lab.hosaily.commons.response.wechat.SessionKeyResponse;
import com.lab.hosaily.core.account.entity.XcxAccount;
import com.rab.babylon.commons.security.exception.HttpClientException;
import com.rab.babylon.commons.utils.HttpClientUtils;
import com.rab.babylon.commons.utils.ObjectUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class XcxUtils{

    private final static Charset CHARSET = Charset.forName("UTF-8");
    private final static String GET_SESSION_KEY = "https://api.weixin.qq.com/sns/jscode2session";

    private XcxUtils(){

    }

    /**
     * 用户信息解密
     */
    public static XcxAccount decrypt(String encrytedData, String sessionKey, String iv) throws Exception{
        if(StringUtils.isBlank(encrytedData)){
            throw new IllegalArgumentException("需解密的用户信息不能为空");
        }
        if(StringUtils.isBlank(sessionKey)){
            throw new IllegalArgumentException("SessionKey不能为空");
        }
        if(StringUtils.isBlank(iv)){
            throw new IllegalArgumentException("加密偏移量不能为空");
        }

        //加密内容
        byte[] dataByte = Base64.decodeBase64(encrytedData);
        //密钥
        byte[] keyByte = Base64.decodeBase64(sessionKey);
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv);

        //密钥长度
        int length = 16;
        if(keyByte.length % length != 0){
            int groups = keyByte.length / length + (keyByte.length % length != 0 ? 1 : 0);
            byte[] temp = new byte[groups * length];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
            keyByte = temp;
        }

        //解密
        Security.addProvider(new BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);

        byte[] resultByte = cipher.doFinal(dataByte);

        if(resultByte != null && resultByte.length > 0){
            String json = new String(resultByte, CHARSET);

            return ObjectUtils.deserialize(json, XcxAccount.class);
        }

        return null;
    }

    /**
     * 根据AppId,AppSecret,code查询用户openId和sessionKey
     */
    public static SessionKeyResponse getOpenIdAndSessionKey(String appId, String secret, String code) throws Exception{
        if(StringUtils.isBlank(appId)){
            throw new IllegalArgumentException("小程序AppId不能为空");
        }
        if(StringUtils.isBlank(secret)){
            throw new IllegalArgumentException("小程序AppSecret不能为空");
        }
        if(StringUtils.isBlank(code)){
            throw new IllegalArgumentException("code不能为空");
        }

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("appid", appId);
        params.put("secret", secret);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");

        CloseableHttpClient client = null;
        HttpGet get = null;

        try{
            client = HttpClientUtils.createHttpClient();
            get = HttpClientUtils.getGet(GET_SESSION_KEY, params);

            HttpResponse response = client.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String json = EntityUtils.toString(response.getEntity());

                if(!StringUtils.isBlank(json)){
                    return ObjectUtils.deserialize(json, SessionKeyResponse.class);
                }

                return null;
            }

            throw new HttpClientException(response.getEntity().toString());
        }finally{
            client.close();
        }
    }
}
