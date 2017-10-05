package com.lab.hosaily.commons.utils;

import com.lab.hosaily.core.curricula.consts.MediaType;

public class MediaUtils{

    //媒体下载API
    public final static String DOWNLOAD_API = "/api/1.0/media/download/";
    //音频存放目录
    public final static String AUDIO_DIR = "/WEB-INF/media/audio/";
    //视频存放目录
    public final static String VIDEO_DIR = "/WEB-INF/media/vedio/";
    //图片存放目录
    public final static String PICTURE_DIR = "/WEB-INF/media/picture/";
    //文本存放目录
    public final static String TEXT_DIR = "/WEB-INF/media/text/";
    //未知类型目录
    public final static String UNKNOWN_DIR = "/WEB-INF/media/unknown/";
    //临时文件目录
    public final static String TEMP_DIR = "/WEB-INF/media/temp/";

    private MediaUtils(){

    }

    /**
     * 查询媒体存放目录
     */
    public static String getDir(MediaType type){
        if(type == null){
            return UNKNOWN_DIR;
        }

        switch(type){
            case UNKNOWN:
                return UNKNOWN_DIR;
            case AUDIO:
                return AUDIO_DIR;
            case VIDEO:
                return VIDEO_DIR;
            case IMG:
                return PICTURE_DIR;
            case TEXT:
                return TEXT_DIR;
            default:
                return UNKNOWN_DIR;
        }
    }
}
