package com.lab.hosaily.core.account.entity;


import com.google.gson.annotations.SerializedName;
import com.lab.hosaily.core.account.consts.WeChatSex;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

public class WeChatAccount implements Create, Update{

    private String id;
    @SerializedName(value = "openId", alternate = {"openId", "openid"})
    private String openId;
    @SerializedName(value = "unionId", alternate = {"unionId", "unionid"})
    private String unionId;
    private Boolean subscribe;
    private String nickname;
    private WeChatSex sex;
    private String language;
    private String city;
    private String province;
    private String country;
    @SerializedName(value = "headImgUrl", alternate = {"headImgUrl", "headimgurl"})
    private String headImgUrl;
    @SerializedName(value = "subscribeTime", alternate = {"subscribeTime", "subscribe_time"})
    private Date subscribeTime;
    private String remark;
    private UsingState state;
    private Date createTime;
    private Date updateTime;

    public WeChatAccount(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getOpenId(){
        return openId;
    }

    public void setOpenId(String openId){
        this.openId = openId;
    }

    public String getUnionId(){
        return unionId;
    }

    public void setUnionId(String unionId){
        this.unionId = unionId;
    }

    public Boolean getSubscribe(){
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe){
        this.subscribe = subscribe;
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

    public String getHeadImgUrl(){
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl){
        this.headImgUrl = headImgUrl;
    }

    public Date getSubscribeTime(){
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime){
        this.subscribeTime = subscribeTime;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    @Override
    public Date getCreateTime(){
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime(){
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
