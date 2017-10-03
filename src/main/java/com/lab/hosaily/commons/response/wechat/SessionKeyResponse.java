package com.lab.hosaily.commons.response.wechat;

import com.google.gson.annotations.SerializedName;

public class SessionKeyResponse{

    @SerializedName(value = "openId", alternate = {"openId", "openid", "open_id"})
    private String openId;
    @SerializedName(value = "sessionKey", alternate = {"sessionKey", "session_key"})
    private String sessionKey;
    @SerializedName(value = "unionId", alternate = {"unionId", "unionid"})
    private String unionId;
    private Integer errcode;
    private String errmsg;

    public SessionKeyResponse(){

    }

    public String getOpenId(){
        return openId;
    }

    public void setOpenId(String openId){
        this.openId = openId;
    }

    public String getSessionKey(){
        return sessionKey;
    }

    public void setSessionKey(String sessionKey){
        this.sessionKey = sessionKey;
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
