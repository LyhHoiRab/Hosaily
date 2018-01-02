package com.lab.hosaily.core.client.consts;

import com.google.gson.annotations.SerializedName;

/**
 * 支付状态
 */
public enum PayState{

    //申请
    @SerializedName("0")
    APPLY(0, "申请"),
    //待确认
    @SerializedName("1")
    UNCONFIRMED(1, "待确认"),
    //已付款
    @SerializedName("2")
    PAID(2, "已付款"),
    //已审核
    @SerializedName("3")
    AUDITED(3, "已审核"),
    //失败
    FAIL(4, "支付失败");

    private int id;
    private String description;

    private PayState(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static PayState getById(int id){
        for(PayState state : PayState.values()){
            if(state.getId() == id){
                return state;
            }
        }

        throw new IllegalArgumentException("未知的支付状态常量");
    }
}
