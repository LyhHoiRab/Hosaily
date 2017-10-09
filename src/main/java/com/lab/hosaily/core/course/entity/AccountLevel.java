package com.lab.hosaily.core.course.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

public class AccountLevel implements Create, Update{

    //ID
    private String id;
    //等级ID
    private String levelId;
    //账户ID
    private String accountId;
    //有效天数
    private Integer effective;
    //是否有效
    private Boolean isValid;
    //生效时间
    private Date startTime;
    //失效时间
    private Date endTime;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public AccountLevel(){

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

    public String getAccountId(){
        return accountId;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public Integer getEffective(){
        return effective;
    }

    public void setEffective(Integer effective){
        this.effective = effective;
    }

    public Boolean getIsValid(){
        return isValid;
    }

    public void setIsValid(Boolean isValid){
        this.isValid = isValid;
    }

    public Date getStartTime(){
        return startTime;
    }

    public void setStartTime(Date startTime){
        this.startTime = startTime;
    }

    public Date getEndTime(){
        return endTime;
    }

    public void setEndTime(Date endTime){
        this.endTime = endTime;
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
