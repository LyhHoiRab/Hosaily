package com.lab.hosaily.core.sell.entity;

import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

/**
 * 账户课程权限表
 */
public class AccountCourse{

    //ID
    private String id;
    //账户ID
    private String accountId;
    //课程ID
    private String courseId;
    //有效天数
    private Integer effective;
    //生效时间
    private Date forceTime;
    //有效期
    private Date deadline;
    //状态
    private UsingState state;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public AccountCourse(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getAccountId(){
        return accountId;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public String getCourseId(){
        return courseId;
    }

    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    public Integer getEffective(){
        return effective;
    }

    public void setEffective(Integer effective){
        this.effective = effective;
    }

    public Date getForceTime(){
        return forceTime;
    }

    public void setForceTime(Date forceTime){
        this.forceTime = forceTime;
    }

    public Date getDeadline(){
        return deadline;
    }

    public void setDeadline(Date deadline){
        this.deadline = deadline;
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
