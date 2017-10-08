package com.lab.hosaily.core.course.consts;

import com.google.gson.annotations.SerializedName;

public enum MediaType{

    @SerializedName("0")
    UNKNOWN(0, "未知"),
    @SerializedName("1")
    AUDIO(1, "音频"),
    @SerializedName("2")
    VIDEO(2, "视频"),
    @SerializedName("3")
    IMG(3, "图片"),
    @SerializedName("4")
    TEXT(4, "文本");

    private int id;
    private String description;

    private MediaType(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static MediaType getById(int id){
        for(MediaType type : MediaType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        return UNKNOWN;
    }
}
