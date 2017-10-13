package com.lab.hosaily.core.account.entity;

import com.google.gson.annotations.SerializedName;
import com.lab.hosaily.core.account.consts.Gender;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.Map;

public class XcxAccount implements Create, Update{

    //ID
    private String id;
    //appId
    private String appId;
    //unionId
    private String unionId;
    //openId
    private String openId;
    //昵称
    @SerializedName(value = "nickname", alternate = {"nickName"})
    private String nickname;
    //性别
    private Gender gender;
    //使用语言
    private String language;
    //国家
    private String country;
    //省
    private String province;
    //市
    private String city;
    //头像
    private String avatarUrl;
    //使用状态
    private UsingState state;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //水印
    private Map<String, Object> watermark;

    public XcxAccount(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getAppId(){
        return appId;
    }

    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getUnionId(){
        return unionId;
    }

    public void setUnionId(String unionId){
        this.unionId = unionId;
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

    public Gender getGender(){
        return gender;
    }

    public void setGender(Gender gender){
        this.gender = gender;
    }

    public String getLanguage(){
        return language;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getProvince(){
        return province;
    }

    public void setProvince(String province){
        this.province = province;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getAvatarUrl(){
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
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

    public Map<String, Object> getWatermark(){
        return watermark;
    }

    public void setWatermark(Map<String, Object> watermark){
        this.watermark = watermark;
    }
}
