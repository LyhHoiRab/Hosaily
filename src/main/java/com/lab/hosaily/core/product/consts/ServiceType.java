package com.lab.hosaily.core.product.consts;

import com.google.gson.annotations.SerializedName;

public enum ServiceType{

    @SerializedName("0")
    ONE_TO_ONE(0, "一对一跟踪服务"),

    @SerializedName("1")
    VIDEO_LEARNING_PERMISSION(1, "视频学习权限");

    private int id;
    private String description;

    private ServiceType(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static ServiceType getById(int id){
        for(ServiceType type : ServiceType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new IllegalArgumentException("未知的服务类型常量");
    }
}
