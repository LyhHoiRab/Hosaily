package com.lab.hosaily.core.sell.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;

import java.util.Date;

/**
 * 支付记录
 */
public class PayLogs implements Create, Update{

    //ID
    private String id;
    //订单记录
    private Order order;
    //支付金额
    private Double pay;
    //支付方式
    private PaymentType payment;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public PayLogs(){

    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public Order getOrder(){
        return order;
    }

    public void setOrder(Order order){
        this.order = order;
    }

    public Double getPay(){
        return pay;
    }

    public void setPay(Double pay){
        this.pay = pay;
    }

    public PaymentType getPayment(){
        return payment;
    }

    public void setPayment(PaymentType payment){
        this.payment = payment;
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
