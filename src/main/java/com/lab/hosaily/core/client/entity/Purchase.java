package com.lab.hosaily.core.client.entity;

import com.lab.hosaily.core.client.consts.PurchaseState;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

/**
 *  购买记录
 */
public class Purchase implements Create, Update{

    //ID
    private String id;
    //账户ID
    private String accountId;
    //企业ID
    private String organizationId;
    //协议
    private Agreement agreement;
    //支付记录
    private List<Payment> payments;
    //下单时间
    private Date orderTime;
    //流程状态
    private PurchaseState purchaseState;
    //状态
    private UsingState state;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public Purchase(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public UsingState getState() {
        return state;
    }

    public void setState(UsingState state) {
        this.state = state;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public PurchaseState getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(PurchaseState purchaseState) {
        this.purchaseState = purchaseState;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
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
