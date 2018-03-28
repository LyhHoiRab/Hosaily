package com.lab.hosaily.core.course.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

/**
 * Created by miku03 on 2018/3/19.
 */
public class Project implements Create, Update {

    private String id;
    private String title;
    private String num;
    private Double price;
    private String status;
    private String resultId;

    private Double originalPrice;
    private String summry;
    private String needKonw;
    private String questionCount;
    private String completedCount;
    private String shareEnable;
    private String order;
    private String timeLimit;

    private String imgUrl;


    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //企业ID
    private String organizationId;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getSummry() {
        return summry;
    }

    public void setSummry(String summry) {
        this.summry = summry;
    }

    public String getNeedKonw() {
        return needKonw;
    }

    public void setNeedKonw(String needKonw) {
        this.needKonw = needKonw;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

    public String getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(String completedCount) {
        this.completedCount = completedCount;
    }

    public String getShareEnable() {
        return shareEnable;
    }

    public void setShareEnable(String shareEnable) {
        this.shareEnable = shareEnable;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
