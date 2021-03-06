package com.lab.hosaily.core.course.entity;

import com.rab.babylon.core.account.entity.UserExpand;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.Sex;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

public class Advisor implements Create, Update{

    //ID
    private String id;
    //名称
    private String name;
    //昵称
    private String nickname;
    //性别
    private Sex sex;
    //企业ID
    private String organizationId;
    //年龄
    private Integer age;
    //介绍
    private String introduction;
    //头像
    private String headImgUrl;
    //排序
    private Integer sort;
    //状态
    private UsingState state;
    //扩展信息
    private List<UserExpand> expand;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //荣誉
    private String honor;
    //简介
    private String summary;

    public Advisor(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public Sex getSex(){
        return sex;
    }

    public void setSex(Sex sex){
        this.sex = sex;
    }

    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public String getIntroduction(){
        return introduction;
    }

    public void setIntroduction(String introduction){
        this.introduction = introduction;
    }

    public String getHeadImgUrl(){
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl){
        this.headImgUrl = headImgUrl;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    public List<UserExpand> getExpand(){
        return expand;
    }

    public void setExpand(List<UserExpand> expand){
        this.expand = expand;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public String getOrganizationId(){
        return organizationId;
    }

    public void setOrganizationId(String organizationId){
        this.organizationId = organizationId;
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

    public String getHonor() {
        return honor;
    }

    public void setHonor(String honor) {
        this.honor = honor;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
