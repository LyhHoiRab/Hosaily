package com.lab.hosaily.core.curricula.entity;

import com.lab.hosaily.core.curricula.consts.MediaType;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

public class Media implements Create, Update{

    private String id;
    private String fileName;
    private String suffix;
    private String url;
    private String md5;
    private Long size;
    private String remark;
    private MediaType type;
    private UsingState state;
    private Date createTime;
    private Date updateTime;

    public Media(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getSuffix(){
        return suffix;
    }

    public void setSuffix(String suffix){
        this.suffix = suffix;
    }

    public Long getSize(){
        return size;
    }

    public void setSize(Long size){
        this.size = size;
    }

    public MediaType getType(){
        return type;
    }

    public void setType(MediaType type){
        this.type = type;
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

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getMd5(){
        return md5;
    }

    public void setMd5(String md5){
        this.md5 = md5;
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
