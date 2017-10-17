package com.lab.hosaily.core.account.consts;

import com.google.gson.annotations.SerializedName;

public enum AttentionType{

    @SerializedName("0")
    COLLECT(0, "收藏"),
    @SerializedName("1")
    TRACK(1, "足迹");

    private int id;
    private String description;

    private AttentionType(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static AttentionType getById(int id){
        for(AttentionType type : AttentionType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new IllegalArgumentException("未知的关注类型");
    }
}
