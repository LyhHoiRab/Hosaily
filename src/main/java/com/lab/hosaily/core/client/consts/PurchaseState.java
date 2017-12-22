package com.lab.hosaily.core.client.consts;

import com.google.gson.annotations.SerializedName;

public enum PurchaseState{

    @SerializedName("0")
    ORDERS(0, "已下单"),

    @SerializedName("1")
    AGREEMENT(1, "确认协议"),

    @SerializedName("2")
    PAYING(2, "等待付款"),

    @SerializedName("3")
    PAID(3, "已付款"),

    @SerializedName("4")
    SERVICE(4, "服务"),

    @SerializedName("5")
    FINISH(5, "结束");

    private int id;
    private String description;

    private PurchaseState(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static PurchaseState getById(int id){
        for(PurchaseState state : PurchaseState.values()){
            if(state.getId() == id){
                return state;
            }
        }

        throw new IllegalArgumentException("未知的使用状态常量");
    }
}
