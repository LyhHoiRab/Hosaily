package com.lab.hosaily.core.application.utils.response;

import com.google.gson.annotations.SerializedName;

/**
 * AccessToken响应对象
 */
public class WechatAccessTokenResponse{

    @SerializedName(value = "accessToken", alternate = "access_token")
    private String accessToken;
    @SerializedName(value = "expiresIn", alternate = "expires_in")
    private Integer expiresIn;
    private Integer errcode;
    private String errmsg;

    public WechatAccessTokenResponse(){

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
