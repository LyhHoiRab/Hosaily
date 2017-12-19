package com.lab.hosaily.core.client.entity;

import com.lab.hosaily.core.product.entity.Service;
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
    //名称
    private String name;
    //产品ID
    private String productId;
    //产品价格
    private Double retail;
    //售价
    private Double price;
    //有效期(月)
    private Integer duration;
    //服务
    private List<Service> services;
    //下单时间
    private Date orderTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getRetail() {
        return retail;
    }

    public void setRetail(Double retail) {
        this.retail = retail;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
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
