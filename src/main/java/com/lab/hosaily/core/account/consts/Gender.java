package com.lab.hosaily.core.account.consts;

import com.google.gson.annotations.SerializedName;
import com.rab.babylon.core.consts.entity.Sex;

public enum Gender{

    @SerializedName("0")
    UNKNOWN(0, "未知"),
    @SerializedName("1")
    MALE(1, "男性"),
    @SerializedName("2")
    FEMALE(2, "女性");

    private int id;
    private String description;

    private Gender(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static Gender getById(int id){
        for(Gender gender : Gender.values()){
            if(gender.getId() == id){
                return gender;
            }
        }

        return UNKNOWN;
    }

    public static Sex changeToSex(Gender gender){
        switch(gender){
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
