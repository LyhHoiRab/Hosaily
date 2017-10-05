package com.lab.hosaily.commons.utils;

import org.apache.commons.lang3.StringUtils;

public class FileNameUtils{

    private FileNameUtils(){

    }

    /**
     * 查询文件后缀
     */
    public static String getSuffix(String name){
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("文件名称不能为空");
        }

        int index = name.lastIndexOf(".");

        return index >= 0 ? name.substring(index, name.length()) : "";
    }

    /**
     * 查询不带后缀的文件名称
     */
    public static String getNameWithoutSuffix(String name){
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("文件名称不能为空");
        }

        int index = name.lastIndexOf(".");
        return index > 0 ? name.substring(0, index) : "";
    }
}
