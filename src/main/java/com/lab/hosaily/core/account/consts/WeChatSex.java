package com.lab.hosaily.core.account.consts;

import com.google.gson.annotations.SerializedName;

public enum WeChatSex{

    @SerializedName("0")
    UNKNOWN(0, "未知"),
    @SerializedName("1")
    MALE(1, "男性"),
    @SerializedName("2")
    FEMALE(2, "女性");

    private int id;
    private String description;

    private WeChatSex(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public static WeChatSex getById(int id){
        for(WeChatSex sex : WeChatSex.values()){
            if(sex.getId() == id){
                return sex;
            }
        }

        throw new IllegalArgumentException("未知性别");
    }
}
