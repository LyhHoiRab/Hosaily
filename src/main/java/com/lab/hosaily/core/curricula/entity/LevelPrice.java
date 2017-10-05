package com.lab.hosaily.core.curricula.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

public class LevelPrice implements Create, Update{

    private String id;
    private String levelId;
    private Double price;
    private Integer effective;
    private UsingState state;
    private Date createTime;
    private Date updateTime;

    public LevelPrice(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getLevelId(){
        return levelId;
    }

    public void setLevelId(String levelId){
        this.levelId = levelId;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Integer getEffective(){
        return effective;
    }

    public void setEffective(Integer effective){
        this.effective = effective;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Date getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }
}
