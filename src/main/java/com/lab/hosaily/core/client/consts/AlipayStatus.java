package com.lab.hosaily.core.client.consts;

import com.google.gson.annotations.SerializedName;

public enum AlipayStatus{

    @SerializedName("0")
    WAIT_BUYER_PAY(0, "交易创建，等待买家付款"),
    @SerializedName("1")
    TRADE_CLOSED(1, "未付款交易超时关闭，或支付完成后全额退款"),
    @SerializedName("2")
    TRADE_SUCCESS(2, "交易支付成功"),
    @SerializedName("3")
    TRADE_FINISHED(3, "交易结束，不可退款");

    private int id;
    private String description;

    private AlipayStatus(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static AlipayStatus getById(int id){
        for(AlipayStatus state : AlipayStatus.values()){
            if(state.getId() == id){
                return state;
            }
        }

        throw new IllegalArgumentException("未知的支付宝支付状态常量");
    }
}
