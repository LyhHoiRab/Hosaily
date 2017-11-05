package com.lab.hosaily.core.sell.entity;

import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Delete;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

/**
 * 订单表
 */
public class Order implements Create, Update, Delete{

    //ID
    private String id;
    //订单号
    private String orderId;
    //销售账户
    private String salesAccount;
    //销售用户信息
    private User salesUser;
    //客户账户
    private String clientAccount;
    //客户信息
    private User clientUser;
    //预售价
    private Double price;
    //已付金额
    private Double pay;
    //备注
    private String remark;
    //支付记录
    private List<PayLogs> payLogs;
    //授权记录
    private List<AccreditLogs> accreditLogs;
    //状态
    private UsingState state;
    //是否删除
    private Boolean isDelete;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除时间
    private Date deleteTime;

    public Order(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getOrderId(){
        return orderId;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getSalesAccount(){
        return salesAccount;
    }

    public void setSalesAccount(String salesAccount){
        this.salesAccount = salesAccount;
    }

    public User getSalesUser(){
        return salesUser;
    }

    public void setSalesUser(User salesUser){
        this.salesUser = salesUser;
    }

    public String getClientAccount(){
        return clientAccount;
    }

    public void setClientAccount(String clientAccount){
        this.clientAccount = clientAccount;
    }

    public User getClientUser(){
        return clientUser;
    }

    public void setClientUser(User clientUser){
        this.clientUser = clientUser;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public Double getPay(){
        return pay;
    }

    public void setPay(Double pay){
        this.pay = pay;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public List<PayLogs> getPayLogs(){
        return payLogs;
    }

    public void setPayLogs(List<PayLogs> payLogs){
        this.payLogs = payLogs;
    }

    public List<AccreditLogs> getAccreditLogs(){
        return accreditLogs;
    }

    public void setAccreditLogs(List<AccreditLogs> accreditLogs){
        this.accreditLogs = accreditLogs;
    }

    public UsingState getState(){
        return state;
    }

    public void setState(UsingState state){
        this.state = state;
    }

    @Override
    public Boolean getIsDelete(){
        return isDelete;
    }

    @Override
    public void setIsDelete(Boolean isDelete){
        this.isDelete = isDelete;
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

    @Override
    public Date getDeleteTime(){
        return deleteTime;
    }

    @Override
    public void setDeleteTime(Date deleteTime){
        this.deleteTime = deleteTime;
    }
}
