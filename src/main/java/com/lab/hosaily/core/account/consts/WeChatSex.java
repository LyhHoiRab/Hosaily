package com.lab.hosaily.core.account.consts;

import com.google.gson.annotations.SerializedName;
import com.rab.babylon.core.consts.entity.Sex;

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

    public static Sex changeToSex(WeChatSex sex){
        switch(sex){
            case UNKNOWN:
                return Sex.UNKNOWN;
            case MALE:
                return Sex.MALE;
            case FEMALE:
                return Sex.FEMALE;
            default:
                return Sex.UNKNOWN;
        }
    }
}
