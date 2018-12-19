package com.lab.hosaily.core.app.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

public class OrderProfile implements Create, Update{

    //ID
    private String orderId;
    private String profileId;
//    服务状态0:已经结束/1:服务中
    private String status;
//    服务时长
    private String serviceHour;
//    导师名字
    private String advisorName;


    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceHour() {
        return serviceHour;
    }

    public void setServiceHour(String serviceHour) {
        this.serviceHour = serviceHour;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
