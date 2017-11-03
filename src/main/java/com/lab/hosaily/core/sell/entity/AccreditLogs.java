package com.lab.hosaily.core.sell.entity;

import com.lab.hosaily.core.course.entity.Course;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

/**
 * 授权记录
 */
public class AccreditLogs implements Create, Update{

    //ID
    private String id;
    //订单记录
    private Order order;
    //授权账户
    private String accreditAccount;
    //授权用户信息
    private User accreditUser;
    //授权账户
    private String authorizeAccount;
    //受权用户
    private User authorizeUser;
    //授权课程
    private Course course;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public AccreditLogs(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Order getOrder(){
        return order;
    }

    public void setOrder(Order order){
        this.order = order;
    }

    public Course getCourse(){
        return course;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public String getAuthorizeAccount(){
        return authorizeAccount;
    }

    public void setAuthorizeAccount(String authorizeAccount){
        this.authorizeAccount = authorizeAccount;
    }

    public String getAccreditAccount(){
        return accreditAccount;
    }

    public void setAccreditAccount(String accreditAccount){
        this.accreditAccount = accreditAccount;
    }

    public User getAccreditUser(){
        return accreditUser;
    }

    public void setAccreditUser(User accreditUser){
        this.accreditUser = accreditUser;
    }

    public User getAuthorizeUser(){
        return authorizeUser;
    }

    public void setAuthorizeUser(User authorizeAccount){
        this.authorizeUser = authorizeUser;
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
