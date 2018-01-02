package com.lab.hosaily.core.course.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

/**
 * Created by miku03 on 2017/11/27.
 */
public class Record implements Create, Update {
    //ID
    private String id;
    //sim卡号码
    private String sim;
    //打出号码
    private String outGoingNum;
    //本机号码
    private String num;
    //时长
    private String time;
    //录音文件地址
    private String path;
    //使用者名称
    private String userName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    //使用者类型
    private String userType;

    //文件类型
    private String fileType;

    //微信uid_MD5码
    private String uidMd5;

    //企业ID
    private String organizationId;

    public Record() {

    }

    public String getUidMd5() {
        return uidMd5;
    }

    public void setUidMd5(String uidMd5) {
        this.uidMd5 = uidMd5;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getOutGoingNum() {
        return outGoingNum;
    }

    public void setOutGoingNum(String outGoingNum) {
        this.outGoingNum = outGoingNum;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
