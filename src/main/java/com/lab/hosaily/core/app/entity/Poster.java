package com.lab.hosaily.core.app.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

public class Poster implements Create, Update {

//    //ID
//    private String id;
    //创建者（销售id）
    private String sellerId;
    //导师id
    private String advisorId;

    //拼接url（唯一）
    private String compoundUrl;
    //合成图片
    private String compoundImgUrl;


    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //企业ID
    private String organizationId;

    public Poster() {

    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }


    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(String advisorId) {
        this.advisorId = advisorId;
    }

    public String getCompoundUrl() {
        return compoundUrl;
    }

    public void setCompoundUrl(String compoundUrl) {
        this.compoundUrl = compoundUrl;
    }

    public String getCompoundImgUrl() {
        return compoundImgUrl;
    }

    public void setCompoundImgUrl(String compoundImgUrl) {
        this.compoundImgUrl = compoundImgUrl;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
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
