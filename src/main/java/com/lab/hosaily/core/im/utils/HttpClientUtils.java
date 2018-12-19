package com.lab.hosaily.core.im.utils;

import com.lab.hosaily.core.im.consts.UtilsException;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@NoArgsConstructor
public class HttpClientUtils {

    //默认编码
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * 创建非证书Https客户端
     */
    public static CloseableHttpClient createHttpsClient(){
        try{
            TrustStrategy trust = new TrustStrategy(){
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException{
                    return true;
                }
            };

            SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, trust).build();
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context);

            return HttpClients.custom().setSSLSocketFactory(factory).build();
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 创建带Cookie的非证书Https客户端
     */
    public static CloseableHttpClient createHttpsClient(Cookie[] cookies){
        try{
            if(cookies == null || cookies.length == 0){
                return createHttpsClient();
            }

            //创建Cookie store
            BasicCookieStore store = new BasicCookieStore();
            //添加Cookie
            for(Cookie cookie : cookies){
                BasicClientCookie clientCookie = new BasicClientCookie(cookie.getName(), cookie.getValue());
                clientCookie.setDomain(cookie.getDomain());
                clientCookie.setPath(cookie.getPath());
                clientCookie.setSecure(cookie.getSecure());

                store.addCookie(clientCookie);
            }

            //创建Https客户端
            TrustStrategy trust = new TrustStrategy(){
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException{
                    return true;
                }
            };

            SSLContext context = new SSLContextBuilder().loadTrustMaterial(null, trust).build();
            SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(context);

            return HttpClients.custom().setSSLSocketFactory(factory).setDefaultCookieStore(store).build();
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 创建Http客户端
     */
    public static CloseableHttpClient createHttpClient(){
        try{
            return HttpClients.createDefault();
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 获取POST请求
     */
    public static HttpPost post(String url, Map<String, Object> urlParams, Map<String, Object> body){
        return post(url, urlParams, body, DEFAULT_CHARSET, false);
    }

    /**
     * 获取POST请求
     */
    public static HttpPost post(String url, Map<String, Object> urlParams, Object body){
        return post(url, urlParams, body, DEFAULT_CHARSET, false);
    }

    /**
     * 获取POST请求
     */
    public static HttpPost post(String url, Map<String, Object> urlParams, Map<String, Object> body, Charset charset, boolean sort){
        if(StringUtils.isBlank(url)){
            throw new UtilsException("请求路径不能为空");
        }

        if(charset == null){
            throw new UtilsException("请求编码不能为空");
        }

        try{
            url = join(url, urlParams, sort);

            HttpPost post = new HttpPost(url);

            if(body != null && !body.isEmpty()){
                List<NameValuePair> list = new ArrayList<NameValuePair>();

                for(Map.Entry<String, Object> param : body.entrySet()){
                    String paramString = GsonUtils.serialize(param.getValue());

                    list.add(new BasicNameValuePair(param.getKey(), GsonUtils.unpunctuation(paramString)));
                }

                post.setEntity(new UrlEncodedFormEntity(list, charset));
            }

            return post;
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 获取POST请求
     */
    public static HttpPost post(String url, Map<String, Object> urlParams, Object body, Charset charset, boolean sort){
        if(StringUtils.isBlank(url)){
            throw new UtilsException("请求路径不能为空");
        }

        if(charset == null){
            throw new UtilsException("请求编码不能为空");
        }

        try{
            url = join(url, urlParams, sort);

            HttpPost post = new HttpPost(url);

            if(body != null){
                post.setEntity(new StringEntity(GsonUtils.serialize(body), charset));
            }

            return post;
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 获取GET请求
     */
    public static HttpGet get(String url, Map<String, Object> urlParams){
        return get(url, urlParams, DEFAULT_CHARSET, false);
    }

    /**
     * 获取GET请求
     */
    public static HttpGet get(String url, Map<String, Object> urlParams, Charset charset, boolean sort){
        if(StringUtils.isBlank(url)){
            throw new UtilsException("请求路径不能为空");
        }

        if(charset == null){
            throw new UtilsException("请求编码不能为空");
        }

        try{
            url = join(url, urlParams, sort);

            return new HttpGet(url);
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 判断响应是否为200
     */
    public static boolean isOk(HttpResponse response){
        if(response == null){
            return false;
        }

        return HttpStatus.SC_OK == response.getStatusLine().getStatusCode();
    }

    /**
     * 解析响应信息
     */
    public static <T> T parse(HttpResponse response, Class<T> clazz){
        return parse(response, clazz, DEFAULT_CHARSET);
    }

    /**
     * 解析响应信息
     */
    public static <T> T parse(HttpResponse response, Type type){
        return parse(response, type, DEFAULT_CHARSET);
    }

    /**
     * 解析响应信息
     */
    public static <T> T parse(HttpResponse response, Class<T> clazz, Charset charset){
        if(response == null){
            throw new UtilsException("响应信息不能为空");
        }

        if(clazz == null){
            throw new UtilsException("反序列化对象类型不能为空");
        }

        if(charset == null){
            throw new UtilsException("响应编码不能为空");
        }

        try{
            String json = EntityUtils.toString(response.getEntity(), charset);

            return GsonUtils.deserialize(json, clazz);
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 解析响应信息
     */
    public static <T> T parse(HttpResponse response, Type type, Charset charset){
        if(response == null){
            throw new UtilsException("响应信息不能为空");
        }

        if(type == null){
            throw new UtilsException("反序列化对象类型不能为空");
        }

        if(charset == null){
            throw new UtilsException("响应编码不能为空");
        }

        try{
            String json = EntityUtils.toString(response.getEntity(), charset);

            return GsonUtils.deserialize(json, type);
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 从响应对象获取流
     */
    public static InputStream getStream(HttpResponse response){
        if(response == null){
            throw new UtilsException("响应信息不能为空");
        }

        try{
            return response.getEntity().getContent();
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 从响应对象流中写出
     */
    public static void writeTo(HttpResponse response, OutputStream output){
        if(response == null){
            throw new UtilsException("响应信息不能为空");
        }

        if(output == null){
            throw new UtilsException("写到的输出流不能为空");
        }

        try{
            response.getEntity().writeTo(output);
        }catch(Exception e){
            throw new UtilsException(e.getMessage(), e);
        }
    }

    /**
     * 装拼URL
     */
    public static String join(String url, Map<String, Object> urlParams, boolean sort){
        if(StringUtils.isBlank(url)){
            throw new UtilsException("请求路径不能为空");
        }

        if(urlParams == null || urlParams.isEmpty()){
            return url;
        }

        //排序
        if(sort){
            urlParams = sort(urlParams);
        }

        //结果集
        StringBuffer sb = new StringBuffer();

        //顺序遍历
        Iterator<String> iterator = urlParams.keySet().iterator();
        for(int i = 0; iterator.hasNext(); i++){
            if(i > 0){
                sb.append("&");
            }

            String key = iterator.next();
            sb.append(key).append("=").append(urlParams.get(key));
        }

        //装拼到URL
        if(url.lastIndexOf('?') == -1){
            url += '?';
        }else{
            url += '&';
        }

        return url + sb.toString();
    }

    /**
     * 参数排序
     */
    public static Map<String, Object> sort(Map<String, Object> params){
        if(params == null || params.isEmpty()){
            throw new UtilsException("排序的参数列表不能为空");
        }

        Map<String, Object> sorted = new LinkedHashMap<String, Object>();

        //参数名
        List<String> names = new ArrayList<String>(params.keySet());
        Collections.sort(names);

        for(String name : names){
            sorted.put(name, params.get(name));
        }

        return sorted;
    }

    /**
     * 关闭连接
     */
    public static void close(CloseableHttpClient client){
        if(client != null){
            try{
                client.close();
            }catch(Exception e){
                //close anyway
            }
        }
    }
}
