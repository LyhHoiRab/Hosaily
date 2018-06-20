package com.lab.hosaily.core.course.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

/**
 * Created by miku03 on 2018/3/19.
 */
public class Option implements Create, Update {

    private String id;
    private String title;
    private Project project;
    private Question question;
    private Question nestQuestion;
    private String questionOption;
    private String imgUrl;

    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //企业ID
    private String organizationId;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getNestQuestion() {
        return nestQuestion;
    }

    public void setNestQuestion(Question nestQuestion) {
        this.nestQuestion = nestQuestion;
    }

    public String getQuestionOption() {
        return questionOption;
    }

    public void setQuestionOption(String questionOption) {
        this.questionOption = questionOption;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
