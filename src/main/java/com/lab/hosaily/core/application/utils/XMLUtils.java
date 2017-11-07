package com.lab.hosaily.core.application.utils;

import com.lab.hosaily.core.application.utils.consts.WechatXMLConsts;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * XML工具
 */
public class XMLUtils {

    private static Charset CHARSET = Charset.forName("UTF-8");

    private XMLUtils(){

    }

    /**
     * 读取XML字节流
     */
    public static String read(InputStream inputStream) throws Exception{
        StringBuffer sb = new StringBuffer();
        InputStreamReader reader = new InputStreamReader(inputStream, CHARSET);
        BufferedReader buffer = new BufferedReader(reader);
        String s = null;

        while(!StringUtils.isBlank(s = buffer.readLine())){
            sb.append(s);
        }

        inputStream.close();
        reader.close();
        buffer.close();

        return sb.toString();
    }

    /**
     * 解析XML为Map
     */
    public static Map<String, Object> parse(String xml) throws Exception{
        if(StringUtils.isBlank(xml)){
            throw new IllegalArgumentException("xml字符串不能为空");
        }

        Map<String, Object> result = new HashMap<String, Object>();

        //解析xml
        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();

        //遍历节点
        Iterator<Element> iterator = root.elementIterator();
        while(iterator.hasNext()){
            Element element = iterator.next();
            result.put(element.getName(), element.getText());
        }
        return result;
    }

    /**
     * 提取解密节点和收件人节点信息
     */
    public static Map<String, Object> extract(String xml) throws Exception{
        if(StringUtils.isBlank(xml)){
            throw new IllegalArgumentException("提取信息的xml字符串不能为空");
        }

        //解释后信息
        Map<String, Object> parse = parse(xml);
        //提取信息
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(WechatXMLConsts.NODE_ENCRYPT, parse.get(WechatXMLConsts.NODE_ENCRYPT));
        result.put(WechatXMLConsts.NODE_TO_USER_NAME, parse.get(WechatXMLConsts.NODE_TO_USER_NAME));

        return result;
    }

    /**
     * 解密XML
     */
    public static Map<String, Object> decrypt(String xml, String encodingAESKey) throws Exception{
        if(StringUtils.isBlank(xml)){
            throw new IllegalArgumentException("xml字符串不能为空");
        }
        if(StringUtils.isBlank(encodingAESKey)){
            throw new IllegalArgumentException("解密密钥不能为空");
        }

        byte[] aesKey = Base64.decodeBase64(encodingAESKey + "=");
        byte[] original;

        //设置解密模式
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
        cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
        //解密
        original = cipher.doFinal(Base64.decodeBase64(xml));
        //去补位
        int pad = (int) original[original.length -1];
        if(pad < 1 || pad > 32){
            pad = 0;
        }
        byte[] bytes = Arrays.copyOfRange(original, 0, original.length - pad);
        //分离16位随机字符串,网络字节序和AppId
        byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
        int sourceNumber = 0;
        for(int i = 0; i < 4; i++){
            sourceNumber <<= 8;
            sourceNumber |= networkOrder[i] & 0xff;
        }
        //解密后明文
        xml = new String(Arrays.copyOfRange(bytes, 20, 20 + sourceNumber), CHARSET);
        //解析明文
        return parse(xml);
    }

    /**
     * 判断是否加密
     */
    public static boolean isEncrypt(String xml){
        if(StringUtils.isBlank(xml)){
            throw new IllegalArgumentException("xml字符串不能为空");
        }

        return xml.contains(WechatXMLConsts.NODE_ENCRYPT);
    }
}
