package com.lab.hosaily.core.client.consts;

import com.google.gson.annotations.SerializedName;

public enum PayType{

    //微信商户号支付
    @SerializedName("0")
    WECHAT_MERCHANT(0, "微信商户号"),
    //支付宝商家号
    @SerializedName("1")
    ALIPAY_MERCHANT(1, "支付宝");

    private int id;
    private String description;

    private PayType(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static PayType getById(int id){
        for(PayType type : PayType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new IllegalArgumentException("未知的付款类型常量");
    }
}
