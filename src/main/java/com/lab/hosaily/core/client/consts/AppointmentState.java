package com.lab.hosaily.core.client.consts;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AppointmentState{

    @SerializedName("0")
    REGISTER(0, "登记"),

    @SerializedName("1")
    DISPOSE(1, "已处理");

    @Getter
    private int id;
    @Getter
    private String description;

    public static AppointmentState getById(int id){
        for(AppointmentState state : AppointmentState.values()){
            if(state.getId() == id){
                return state;
            }
        }

        throw new IllegalArgumentException("未知的预约状态常量");
    }
}
