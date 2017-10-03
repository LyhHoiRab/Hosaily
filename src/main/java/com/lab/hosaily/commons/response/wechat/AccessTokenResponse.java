package com.lab.hosaily.commons.response.wechat;

import com.google.gson.annotations.SerializedName;

public class AccessTokenResponse{

    @SerializedName(value = "accessToken", alternate = {"accessToken", "access_token"})
    private String accessToken;
    @SerializedName(value = "expiresIn", alternate = {"expiresIn", "expires_in"})
    private Integer expiresIn;
    @SerializedName(value = "refreshToken", alternate = {"refreshToken", "refresh_token"})
    private String refreshToken;
    @SerializedName(value = "openId", alternate = {"openId", "openid"})
    private String openId;
    private String scope;
    @SerializedName(value = "unionId", alternate = {"unionId", "unionid"})
    private String unionId;
    private Integer errcode;
    private String errmsg;

    public AccessTokenResponse(){

    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn(){
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn){
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken(){
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public String getOpenId(){
        return openId;
    }

    public void setOpenId(String openId){
        this.openId = openId;
    }

    public String getScope(){
        return scope;
    }

    public void setScope(String scope){
        this.scope = scope;
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
}
