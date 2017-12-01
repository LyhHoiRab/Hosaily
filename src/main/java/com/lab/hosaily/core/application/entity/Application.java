package com.lab.hosaily.core.application.entity;

import com.lab.hosaily.core.application.consts.ApplicationType;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Delete;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

/**
 * 应用实体
 */
public class Application implements Create, Update, Delete{

    private String id;
    private String name;
    private String appId;
    private String secret;
    private String aesKey;
    private String token;
    private String organizationId;
    private ApplicationType type;
    private UsingState state;
    private Boolean isDelete;
    private Date createTime;
    private Date updateTime;
    private Date deleteTime;

    public Application(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAppId(){
        return appId;
    }

    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getSecret(){
        return secret;
    }

    public void setSecret(String secret){
        this.secret = secret;
    }

    public String getAesKey(){
        return aesKey;
    }

    public void setAesKey(String aesKey){
        this.aesKey = aesKey;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    public ApplicationType getType(){
        return type;
    }

    public void setType(ApplicationType type){
        this.type = type;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    public String getOrganizationId(){
        return organizationId;
    }

    public void setOrganizationId(String organizationId){
        this.organizationId = organizationId;
    }

    @Override
    public Boolean getIsDelete(){
        return isDelete;
    }

    @Override
    public void setIsDelete(Boolean isDelete){
        this.isDelete = isDelete;
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
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public Date getDeleteTime(){
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime){
        this.deleteTime = deleteTime;
    }
}
