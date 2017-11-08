package com.lab.hosaily.core.application.utils.response;

import com.google.gson.annotations.SerializedName;
import com.lab.hosaily.core.account.consts.WeChatSex;

import java.util.Date;

public class WechatUserInfoResponse{

    private Integer subscribe;
    @SerializedName(value = "openId", alternate = "openid")
    private String openId;
    private String nickname;
    private WeChatSex sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    @SerializedName(value = "subscribeTime", alternate = "subscribe_time")
    private Date subscribeTime;
    @SerializedName(value = "unionId", alternate = "unionid")
    private String unionId;
    private String remark;

    public WechatUserInfoResponse(){

    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public Integer getSubscribe(){
        return subscribe;
    }

    public void setSubscribe(Integer subscribe){
        this.subscribe = subscribe;
    }

    public String getOpenId(){
        return openId;
    }

    public void setOpenId(String openId){
        this.openId = openId;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public WeChatSex getSex(){
        return sex;
    }

    public void setSex(WeChatSex sex){
        this.sex = sex;
    }

    public String getLanguage(){
        return language;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getProvince(){
        return province;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getHeadimgurl(){
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl){
        this.headimgurl = headimgurl;
    }

    public Date getSubscribeTime(){
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime){
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId(){
        return unionId;
    }

    public void setUnionId(String unionId){
        this.unionId = unionId;
    }
}
