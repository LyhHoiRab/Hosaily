package com.lab.hosaily.core.sell.entity;

import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

/**
 * 账户课程权限表
 */
public class AccountCourse implements Create, Update{

    //ID
    private String id;
    //账户ID
    private String accountId;
    //用户信息
    private User user;
    //课程ID
    private Course course;
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

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Course getCourse(){
        return course;
    }

    public void setCourse(Course course){
        this.course = course;
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
