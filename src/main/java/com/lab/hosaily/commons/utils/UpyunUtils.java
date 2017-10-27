package com.lab.hosaily.commons.utils;

import main.java.com.UpYun;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

/**
 * 又拍云工具类
 */
public class UpyunUtils{

    private final static String BUCKET_NAME = "kuliao";
    private final static String OPERATOR_NAME = "unesmall";
    private final static String OPERATOR_PWD = "unesmall123456";

    //课程封面目录
    public final static String COURSE_COVER_DIR = "/course/cover/";
    //用户头像目录
    public final static String USER_HEAD_DIR = "/user/head/";
    //导师头像目录
    public final static String ADVISOR_HEAD_DIR = "/advisor/head/";
    //会员等级图片目录
    public final static String LEVEL_COVER_DIR = "/level/cover/";
    //富文本资源目录
    public final static String UEDITOR_RESOURCE_DIR = "/ueditor/resource/";
    //访问外链
    public final static String URL = "http://kuliao.b0.upaiyun.com/";

    //UpYun
    private static UpYun upyun;

    private UpyunUtils(){

    }

    private static UpYun getUpyun(){
        if(upyun == null){
            upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_PWD);
            upyun.setDebug(false);
            //2分钟超时时长
            upyun.setTimeout(120);
        }

        return upyun;
    }

    public static boolean upload(String uploadPath, CommonsMultipartFile file){
        if(StringUtils.isBlank(uploadPath)){
            throw new IllegalArgumentException("上传路径不能为空");
        }

        return getUpyun().writeFile(uploadPath, file.getBytes());
    }

    public static Map<String, String> getFileInfo(String uploadPath){
        if(StringUtils.isBlank(uploadPath)){
            throw new IllegalArgumentException("上传路径不能为空");
        }

        return getUpyun().getFileInfo(uploadPath);
    }

    public static boolean delete(String uploadPath){
        if(StringUtils.isBlank(uploadPath)){
            throw new IllegalArgumentException("上传路径不能为空");
        }

        return getUpyun().deleteFile(uploadPath);
    }
}
