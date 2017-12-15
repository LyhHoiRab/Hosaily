package com.lab.hosaily.core.course.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

/**
 * Created by miku03 on 2017/11/27.
 */
public class SimNumUser implements Create, Update {
    //sim卡号码
    private String sim;
    //本机号码
    private String num;
    //使用者名称
    private String userName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    //使用者类型
    private String userType;

    //企业ID
    private String organizationId;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
