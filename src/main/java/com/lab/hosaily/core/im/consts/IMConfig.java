package com.lab.hosaily.core.im.consts;

import com.lab.hosaily.core.im.utils.SignatureUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.FileInputStream;

public class IMConfig {

    //个人
//    public static final String SDK_APPID     = "1400069579";
//    public static final String ADMINISTRATOR = "HoiWah";

    //新营家
//    public static final String SDK_APPID     = "1400141111";
//    public static final String ADMINISTRATOR = "xinyingjia";

    //永恒情书
    public static final String SDK_APPID     = "1400145501";
    public static final String ADMINISTRATOR = "admin1";
//
    public static final String ADMINISTRATOR_SIG;

    static{
        try{
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource(Constants.IM_PRIVATE_KEY_HSL);
            System.out.println("Constants.IM_PRIVATE_KEY: " + Constants.IM_PRIVATE_KEY_HSL);
            //读取文件
            FileInputStream stream = new FileInputStream(resource.getFile());
            //缓冲
            byte[] buffer = new byte[4096];
            //结果集
            StringBuffer sb = new StringBuffer();
            //读取长度
            int length = 0;

            while((length = stream.read(buffer)) != -1){
                sb.append(new String(buffer, 0, length));
            }
            //关闭流
            stream.close();

            ADMINISTRATOR_SIG = SignatureUtils.get(IMConfig.SDK_APPID, IMConfig.ADMINISTRATOR, sb.toString());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
