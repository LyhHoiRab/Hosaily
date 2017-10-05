package com.lab.hosaily.core.application.consts;

import com.google.gson.annotations.SerializedName;

public enum ApplicationType{

    @SerializedName(value = "0")
    WEB(0, "网站应用"),
    @SerializedName(value = "1")
    XCX(1, "小程序"),
    @SerializedName(value = "2")
    WECHAT(2, "微信公众账号");

    private int id;
    private String description;

    private ApplicationType(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public static ApplicationType getById(int id){
        for(ApplicationType type : ApplicationType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new IllegalArgumentException("未知应用类型");
    }
}
