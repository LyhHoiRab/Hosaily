package com.lab.hosaily.core.app.entity;

import com.google.gson.annotations.SerializedName;
import com.lab.hosaily.core.account.consts.WeChatSex;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

public class InviteWeChatAccount implements Create, Update{

    private String id;
    private String appId;
    @SerializedName(value = "openId", alternate = {"openid"})
    private String openId;
    @SerializedName(value = "unionId", alternate = {"union_id", "unionid"})
    private String unionId;
    private Boolean subscribe;
    private String nickname;
    private WeChatSex sex;
    private String language;
    private String city;
    private String province;
    private String country;
    @SerializedName(value = "headImgUrl", alternate = {"headimgurl"})
    private String headImgUrl;
    @SerializedName(value = "subscribeTime", alternate = {"subscribe_time"})
    private Date subscribeTime;
    private String remark;
    private UsingState state;
    private Date createTime;
    private Date updateTime;
    //纬度
    private Double latitude;
    //经度
    private Double longitude;
    //精度
    private Double precision;
    private String sellerId;
    private String advisorId;

    public InviteWeChatAccount(){

    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
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

    public Double getLatitude(){
        return latitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public Double getPrecision(){
        return precision;
    }

    public void setPrecision(Double precision){
        this.precision = precision;
    }
}
