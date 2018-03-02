package com.lab.hosaily.commons.utils;

import com.rab.babylon.commons.utils.HttpClientUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class YunpianUtils{

    //查账户信息的http地址
    private static final String URI_GET_USER_INFO = "https://sms.yunpian.com/v2/user/get.json";

    //智能匹配模板发送接口的http地址
    private static final String URI_SEND_SMS = "https://sms.yunpian.com/v2/sms/single_send.json";

    //模板发送接口的http地址
    private static final String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

    //发送语音验证码接口的http地址
    private static final String URI_SEND_VOICE = "https://voice.yunpian.com/v2/voice/send.json";

    //绑定主叫、被叫关系的接口http地址
    private static final String URI_SEND_BIND = "https://call.yunpian.com/v2/call/bind.json";

    //解绑主叫、被叫关系的接口http地址
    private static final String URI_SEND_UNBIND = "https://call.yunpian.com/v2/call/unbind.json";

    //编码格式。发送编码格式统一用UTF-8
    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 智能匹配模板
     */
    public static void sendSms(String apikey, String text, String mobile){
        if(StringUtils.isBlank(apikey)){
            throw new IllegalArgumentException("云片API key不能为空");
        }
        if(StringUtils.isBlank(text)){
            throw new IllegalArgumentException("短信模板内容不能为空");
        }
        if(StringUtils.isBlank(mobile)){
            throw new IllegalArgumentException("发送的手机号码不能为空");
        }

        //参数
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);

//        CloseableHttpClient client = HttpClientUtils.
    }
}
