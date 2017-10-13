package com.lab.hosaily.commons.response.wechat;

import com.google.gson.annotations.SerializedName;
import com.lab.hosaily.core.account.consts.WeChatSex;
import com.lab.hosaily.core.account.entity.WeChatAccount;

public class UserInfoResponse{

    @SerializedName(value = "openId", alternate = {"openid", "open_id"})
    private String openId;
    private String nickname;
    private WeChatSex sex;
    private String province;
    private String city;
    private String country;
    @SerializedName(value = "headImgUrl", alternate = {"headimgurl"})
    private String headImgUrl;
    @SerializedName(value = "unionId", alternate = {"union_id", "unionid"})
    private String unionId;
    private Integer errcode;
    private String errmsg;

    public UserInfoResponse(){

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

    public String getUnionId(){
        return unionId;
    }

    public void setUnionId(String unionId){
        this.unionId = unionId;
    }

    public Integer getErrcode(){
        return errcode;
    }

    public void setErrcode(Integer errcode){
        this.errcode = errcode;
    }

    public String getErrmsg(){
        return errmsg;
    }

    public void setErrmsg(String errmsg){
        this.errmsg = errmsg;
    }

    public WeChatAccount changeToWeChatAccount(){
        WeChatAccount account = new WeChatAccount();
        account.setOpenId(this.openId);
        account.setUnionId(this.unionId);
        account.setNickname(this.nickname);
        account.setSex(this.sex);
        account.setProvince(this.province);
        account.setCity(this.city);
        account.setCountry(this.country);
        account.setHeadImgUrl(this.headImgUrl);

        return account;
    }
}
