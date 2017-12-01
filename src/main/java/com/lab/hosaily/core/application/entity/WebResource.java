package com.lab.hosaily.core.application.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;

public class WebResource implements Create, Update{

    //ID
    private String id;
    //描述
    private String description;
    //域名
    private String domain;
    //图片目录
    private String imgUrl;
    //样式目录
    private String cssUrl;
    //JS目录
    private String jsUrl;
    //移动端图片目录
    private String mobileImgUrl;
    //移动端样式目录
    private String mobileCssUrl;
    //移动端JS目录
    private String mobileJsUrl;
    //企业ID
    private String organizationId;
    //使用状态
    private UsingState state;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public WebResource(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getDomain(){
        return domain;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public String getCssUrl(){
        return cssUrl;
    }

    public void setCssUrl(String cssUrl){
        this.cssUrl = cssUrl;
    }

    public String getJsUrl(){
        return jsUrl;
    }

    public void setJsUrl(String jsUrl){
        this.jsUrl = jsUrl;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    public String getOrganizationId(){
        return organizationId;
    }

    public void setOrganizationId(String organizationId){
        this.organizationId = organizationId;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getMobileImgUrl(){
        return mobileImgUrl;
    }

    public void setMobileImgUrl(String mobileImgUrl){
        this.mobileImgUrl = mobileImgUrl;
    }

    public String getMobileCssUrl(){
        return mobileCssUrl;
    }

    public void setMobileCssUrl(String mobileCssUrl){
        this.mobileCssUrl = mobileCssUrl;
    }

    public String getMobileJsUrl(){
        return mobileJsUrl;
    }

    public void setMobileJsUrl(String mobileJsUrl){
        this.mobileJsUrl = mobileJsUrl;
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
