package com.lab.hosaily.commons.utils;

import com.lab.hosaily.commons.response.wechat.WechatMerchantPayCallbackResponse;
import com.lab.hosaily.commons.response.wechat.WechatMerchantPayResponse;
import com.lab.hosaily.core.client.entity.WechatMerchantPay;
import com.rab.babylon.commons.utils.HttpClientUtils;
import com.rab.babylon.commons.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * 微信商户支付接口
 */
public class WechatMerchantPayUtils{

    //预支付接口
    private static final String PREPAY_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private WechatMerchantPayUtils(){

    }

    /**
     * 预支付
     */
    public static WechatMerchantPayResponse prepay(WechatMerchantPay wechatMerchantPay) throws Exception{
        if(wechatMerchantPay == null){
            throw new IllegalArgumentException("微信商户支付信息不能为空");
        }

        Map<String, String> params = getParams(wechatMerchantPay);
        String xml = toXml(params);

        CloseableHttpClient client = HttpClientUtils.createHttpClient();

        HttpPost post = new HttpPost(PREPAY_API);
        StringEntity entity = new StringEntity(xml, "UTF-8");
        post.setEntity(entity);

        try{
            HttpResponse response = client.execute(post);
            if(response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                String result = EntityUtils.toString(response.getEntity());
                result = new String(result.getBytes("ISO-8859-1"), "UTF-8");

                if(StringUtils.isBlank(result)){
                    throw new RuntimeException("预支付失败");
                }

                return toWechatMerchantPayResponse(result);
            }

            throw new RuntimeException("预支付失败");
        }finally{
            HttpClientUtils.close(client);
        }
    }

    /**
     * 生成签名
     */
    public static String sign(Map<String, String> params, String key) throws Exception{
        if(params == null && params.isEmpty()){
            return "";
        }
        if(StringUtils.isBlank(key)){
            throw new IllegalArgumentException("商户appKey不能为空");
        }

        List<String> list = new ArrayList<String>();
        for(Map.Entry<String, String> entry : params.entrySet()){
            if(!StringUtils.isBlank(entry.getValue())){
                list.add(entry.getKey() + "=" + entry.getValue());
            }
        }
        //排序参数
        String[] sort = list.toArray(new String[]{});
        Arrays.sort(sort);
        //生成参数列表字符串
        StringBuffer sb = new StringBuffer();
        for(String param : sort){
            sb.append(param).append("&");

        }
        sb.append("key=" + key);

        return MD5Utils.encrypt(sb.toString()).toUpperCase();
    }

    /**
     * 生成参数列表
     */
    public static Map<String, String> getParams(WechatMerchantPay wechatMerchantPay) throws Exception{
        if(wechatMerchantPay == null){
            throw new IllegalArgumentException("微信商户支付信息不能为空");
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", wechatMerchantPay.getAppId());
        params.put("mch_id", wechatMerchantPay.getMchId());
        params.put("nonce_str", wechatMerchantPay.getNonceStr());
        params.put("sign_type", wechatMerchantPay.getSignType());
        params.put("body", wechatMerchantPay.getBody());
        params.put("total_fee", wechatMerchantPay.getTotalFee().toString());
        params.put("notify_url", wechatMerchantPay.getNotifyUrl());
        params.put("trade_type", wechatMerchantPay.getTradeType());
        params.put("openid", wechatMerchantPay.getOpenId());
        params.put("out_trade_no", wechatMerchantPay.getOutTradeNo());

//        List<String> list = new ArrayList<String>();
//        for(Map.Entry<String, String> entry : params.entrySet()){
//            if(!StringUtils.isBlank(entry.getValue())){
//                list.add(entry.getKey() + "=" + entry.getValue());
//            }
//        }
//        //排序参数
//        String[] sort = list.toArray(new String[]{});
//        Arrays.sort(sort);
//        //生成参数列表字符串
//        StringBuffer sb = new StringBuffer();
//        for(String param : sort){
//            sb.append(param).append("&");
//
//        }
//        sb.append("key=" + wechatMerchantPay.getKey());

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        //生成参数列表字符串
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < keys.size(); i++){
            String key = keys.get(i);
            String value = params.get(key);

            sb.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        params.put("sign", MD5Utils.encrypt(sb.toString()).toUpperCase());
        return params;
    }

    /**
     * 生成JSAPI支付参数列表
     */
    public static Map<String, String> getJsapiParams(WechatMerchantPay wechatMerchantPay) throws Exception{
        if(wechatMerchantPay == null){
            throw new IllegalArgumentException("微信商户支付信息不能为空");
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", wechatMerchantPay.getAppId());
        params.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        params.put("nonceStr", wechatMerchantPay.getNonceStr());
        params.put("package", "prepay_id=" + wechatMerchantPay.getPrepayId());
        params.put("signType", wechatMerchantPay.getSignType());
        params.put("paySign", sign(params, wechatMerchantPay.getKey()));

        return params;
    }

    /**
     * 转参数列表转XML
     */
    public static String toXml(Map<String, String> params){
        if(params == null || params.isEmpty()){
            throw new IllegalArgumentException("参数列表不能为空");
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");

        for(Map.Entry<String, String> entry : params.entrySet()){
            sb.append("<")
              .append(entry.getKey())
              .append(">")
              .append("<![CDATA[")
              .append(entry.getValue())
              .append("]]>")
              .append("</")
              .append(entry.getKey())
              .append(">");
        }
        sb.append("</xml>");

        return sb.toString();
    }

    /**
     * 生成微信商户支付响应对象
     */
    public static WechatMerchantPayResponse toWechatMerchantPayResponse(String xml) throws Exception{
        if(StringUtils.isBlank(xml)){
            throw new IllegalArgumentException("xml信息不能为空");
        }

        WechatMerchantPayResponse response = new WechatMerchantPayResponse();

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();

        Iterator<Element> iterator = root.elementIterator();

        while(iterator.hasNext()){
            Element element = iterator.next();
            String name = element.getName();

            switch(name){
                case "appid":
                    response.setAppId(element.getText().trim());
                    break;
                case "mch_id":
                    response.setMchId(element.getText().trim());
                    break;
                case "nonce_str":
                    response.setNonceStr(element.getText().trim());
                    break;
                case "sign":
                    response.setSign(element.getText().trim());
                    break;
                case "return_code":
                    response.setReturnCode(element.getText().trim());
                    break;
                case "return_msg":
                    response.setReturnMsg(element.getText().trim());
                    break;
                case "result_code":
                    response.setResultCode(element.getText().trim());
                    break;
                case "err_code":
                    response.setErrCode(element.getText().trim());
                    break;
                case "err_code_des":
                    response.setErrCodeDes(element.getText().trim());
                    break;
                case "prepay_id":
                    response.setPrepayId(element.getText().trim());
                    break;
                default:
                    break;
            }
        }

        return response;
    }

    /**
     * 生成微信商户支付回调相应对象
     */
    public static WechatMerchantPayCallbackResponse toWechatMerchantPayCallbackResponse(String xml) throws Exception{
        if(StringUtils.isBlank(xml)){
            throw new IllegalArgumentException("xml信息不能为空");
        }

        WechatMerchantPayCallbackResponse response = new WechatMerchantPayCallbackResponse();

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();

        Iterator<Element> iterator = root.elementIterator();

        while(iterator.hasNext()) {
            Element element = iterator.next();
            String name = element.getName();

            switch(name){
                case "return_code":
                    response.setReturnCode(element.getText().trim());
                    break;
                case "return_msg":
                    response.setReturnMsg(element.getText().trim());
                    break;
                case "appid":
                    response.setAppId(element.getText().trim());
                    break;
                case "mch_id":
                    response.setMchId(element.getText().trim());
                    break;
                case "nonce_str":
                    response.setNonceStr(element.getText().trim());
                    break;
                case "sign":
                    response.setSign(element.getText().trim());
                    break;
                case "sign_type":
                    response.setSignType(element.getText().trim());
                    break;
                case "result_code":
                    response.setResultCode(element.getText().trim());
                    break;
                case "err_code":
                    response.setErrCode(element.getText().trim());
                    break;
                case "err_code_des":
                    response.setErrCodeDes(element.getText().trim());
                    break;
                case "openid":
                    response.setOpenId(element.getText().trim());
                    break;
                case "total_fee":
                    response.setTotalFee(Integer.valueOf(element.getText().trim()));
                    break;
                case "out_trade_no":
                    response.setOutTradeNo(element.getText().trim());
                    break;
                default:
                    break;
            }
        }

        return response;
    }
}
