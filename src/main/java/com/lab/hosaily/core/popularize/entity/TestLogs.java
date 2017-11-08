package com.lab.hosaily.core.popularize.entity;

import java.util.Date;

public class TestLogs{

    private String accountId;
    private String testId;
    private Date createTime;

    public TestLogs(){

    }

    public String getAccountId(){
        return accountId;
    }

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public String getTestId(){
        return testId;
    }

    public void setTestId(String testId){
        this.testId = testId;
    }
}
