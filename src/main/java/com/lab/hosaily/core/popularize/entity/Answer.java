package com.lab.hosaily.core.popularize.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

/**
 * 题库答案
 */
public class Answer implements Create, Update{

    //ID
    private String id;
    //题库ID
    private String libraryId;
    //内容
    private String content;
    //分析
    private String analysis;
    //排序
    private Integer sort;
    //是否正确
    private Boolean isTrue;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public Answer(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getLibraryId(){
        return libraryId;
    }

    public void setLibraryId(String libraryId){
        this.libraryId = libraryId;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getAnalysis(){
        return analysis;
    }

    public void setAnalysis(String analysis){
        this.analysis = analysis;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public Boolean getIsTrue(){
        return isTrue;
    }

    public void setIsTrue(Boolean isTrue){
        this.isTrue = isTrue;
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
