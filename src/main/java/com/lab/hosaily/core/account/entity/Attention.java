package com.lab.hosaily.core.account.entity;

import com.lab.hosaily.core.account.consts.AttentionType;
import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.core.base.entity.Create;

import java.util.Date;

public class Attention implements Create{

    private String id;
    private String accountId;
    private Course course;
    private AttentionType type;
    private Date createTime;

    public Attention(){

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

    public Course getCourse(){
        return course;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public AttentionType getType(){
        return type;
    }

    public void setType(AttentionType type){
        this.type = type;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }
}
