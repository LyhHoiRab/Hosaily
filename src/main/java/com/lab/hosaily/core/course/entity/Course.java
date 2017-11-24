package com.lab.hosaily.core.course.entity;

import com.lab.hosaily.core.course.consts.CourseKind;
import com.lab.hosaily.core.course.consts.CourseType;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

public class Course implements Create, Update{

    //ID
    private String id;
    //课程层级类型
    private CourseType type;
    //课程类型
    private CourseKind kind;
    //标题
    private String title;
    //图文介绍
    private String introduction;
    //概要
    private String summary;
    //封面
    private String cover;
    //价格
    private Double price;
    //点赞量
    private Integer likes;
    //浏览量
    private Integer view;
    //评论量
    private Integer comments;
    //排序
    private Integer sort;
    //父级ID
    private String parentId;
    //企业ID
    private String organizationId;
    //子课程
    private List<Course> children;
    //媒体资源
    private List<Media> media;
    //导师
    private Advisor advisor;
    //限制等级
    private List<Level> level;
    //标签
    private List<Tag> tag;
    //使用状态
    private UsingState state;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public Course(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public CourseType getType(){
        return type;
    }

    public void setType(CourseType type){
        this.type = type;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getIntroduction(){
        return introduction;
    }

    public void setIntroduction(String introduction){
        this.introduction = introduction;
    }

    public String getCover(){
        return cover;
    }

    public void setCover(String cover){
        this.cover = cover;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Integer getLikes(){
        return likes;
    }

    public void setLikes(Integer likes){
        this.likes = likes;
    }

    public Integer getView(){
        return view;
    }

    public void setView(Integer view){
        this.view = view;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public String getParentId(){
        return parentId;
    }

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public List<Course> getChildren(){
        return children;
    }

    public void setChildren(List<Course> children){
        this.children = children;
    }

    public List<Media> getMedia(){
        return media;
    }

    public void setMedia(List<Media> media){
        this.media = media;
    }

    public Advisor getAdvisor(){
        return advisor;
    }

    public void setAdvisor(Advisor advisor){
        this.advisor = advisor;
    }

    public List<Level> getLevel(){
        return level;
    }

    public void setLevel(List<Level> level){
        this.level = level;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    public List<Tag> getTag(){
        return tag;
    }

    public void setTag(List<Tag> tag){
        this.tag = tag;
    }

    public CourseKind getKind(){
        return kind;
    }

    public void setKind(CourseKind kind){
        this.kind = kind;
    }

    public String getSummary(){
        return summary;
    }

    public void setSummary(String summary){
        this.summary = summary;
    }

    public Integer getComments(){
        return comments;
    }

    public void setComments(Integer comments){
        this.comments = comments;
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
}
