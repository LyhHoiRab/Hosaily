package com.lab.hosaily.core.app.entity;

import com.rab.babylon.core.account.entity.UserExpand;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.Sex;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

public class Profile implements Create, Update {

    //ID
    private String id;
    //名称
    private String name;
    //account_id
    private String accountId;
    //性别
    private Sex sex;
    //手机号
    private String mobile;
    //年龄
    private Integer age;
    //备注
    private String comment;
    //头像url
    private String headImgUrl;
    //工作
    private String job;
    //居住地
    private String address;
    //婚龄
    private String marryAge;
    //情感需求
    private String requirement;
    //出现的问题
    private String problem;
    //昵称
    private String nickname;
    //扩展信息
//    private List<News> news;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //企业ID
    private String organizationId;


    //    客户认为的原因
    private String reason;
    //    客户曾修复
    private String fixed;
    //    原生家庭
    private String originalFamily;
    //    难忘经历
    private String unforgettable;
    //    自评
    private String selfAssessment;
    //    对方性格评论
    private String otherAssessment;


    //    咨询师特有资料字段
//    英文名字
    private String englishName;
    //    荣誉资质
    private String aptitude;
    //    擅长
    private String gootAt;
    //    履历
    private String resume;


    private String aLastAgreementId;

    private String aLastState;

    private Date aLastCreateTime;

    private String nCountNum;

    private Integer role;

    private Integer serviceCount;

    private String sellerId;

    private String orderStatus;


    private String orderCount;

    public Profile() {

    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getAptitude() {
        return aptitude;
    }

    public void setAptitude(String aptitude) {
        this.aptitude = aptitude;
    }

    public String getGootAt() {
        return gootAt;
    }

    public void setGootAt(String gootAt) {
        this.gootAt = gootAt;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getaLastAgreementId() {
        return aLastAgreementId;
    }

    public void setaLastAgreementId(String aLastAgreementId) {
        this.aLastAgreementId = aLastAgreementId;
    }

    public String getaLastState() {
        return aLastState;
    }

    public void setaLastState(String aLastState) {
        this.aLastState = aLastState;
    }

    public Date getaLastCreateTime() {
        return aLastCreateTime;
    }

    public void setaLastCreateTime(Date aLastCreateTime) {
        this.aLastCreateTime = aLastCreateTime;
    }

    public String getnCountNum() {
        return nCountNum;
    }

    public void setnCountNum(String nCountNum) {
        this.nCountNum = nCountNum;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getOriginalFamily() {
        return originalFamily;
    }

    public void setOriginalFamily(String originalFamily) {
        this.originalFamily = originalFamily;
    }

    public String getUnforgettable() {
        return unforgettable;
    }

    public void setUnforgettable(String unforgettable) {
        this.unforgettable = unforgettable;
    }

    public String getSelfAssessment() {
        return selfAssessment;
    }

    public void setSelfAssessment(String selfAssessment) {
        this.selfAssessment = selfAssessment;
    }

    public String getOtherAssessment() {
        return otherAssessment;
    }

    public void setOtherAssessment(String otherAssessment) {
        this.otherAssessment = otherAssessment;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMarryAge() {
        return marryAge;
    }

    public void setMarryAge(String marryAge) {
        this.marryAge = marryAge;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

//    public List<News> getNews() {
//        return news;
//    }
//
//    public void setNews(List<News> news) {
//        this.news = news;
//    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
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
