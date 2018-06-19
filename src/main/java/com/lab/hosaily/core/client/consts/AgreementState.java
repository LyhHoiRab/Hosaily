package com.lab.hosaily.core.client.consts;

import com.google.gson.annotations.SerializedName;

public enum AgreementState{

    @SerializedName("0")
    CREATED(0, "已创建"),
    @SerializedName("1")
    WAIT_FOR_FILL(1, "填写资料"),
    @SerializedName("2")
    WAIT_FOR_SIGN(2, "签名确认"),
    @SerializedName("3")
    TAKE_EFFECT(3, "生效"),
    @SerializedName("4")
    NOT_EFFECT(4, "失效");

    private int id;
    private String description;

    private AgreementState(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static AgreementState getById(int id){
        for(AgreementState state : AgreementState.values()){
            if(state.getId() == id){
                return state;
            }
        }

        throw new IllegalArgumentException("未知的协议状态常量");
    }
}
