package com.lab.hosaily.core.app.entity;

import com.rab.babylon.core.account.entity.Account;
import com.rab.babylon.core.account.entity.UserExpand;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.Sex;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

public class Order implements Create, Update{

    //ID
    private String id;
    //订单名
    private String title;
    //客户名
    private String customerName;
    //客户头像
    private String clientHeadImgUrl;
    //订单状态
    private String status;
    //套餐名
    private String servicePackage;
    //微信号
    private String wechatNum;
    //销售account id
    private Profile seller;
    //销售account id
    private String accountId;
    //价格
    private Double price;
    //支付方式
    private String payType;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //企业ID
    private String organizationId;

    //支付宝支付金额
    private Double aliPay;
    //微信支付金额
    private Double wecharPay;
    //paypal支付金额
    private Double payPalPay;
    //银行支付金额
    private Double bankPay;
    //微信支付金额
    private List<OrderProfile> advisors;

    //微信支付金额
    private List<OrderProfile> orderProfiles;

    //微信支付金额
    private List<OrderProfile> updateOrderProfiles;

    //合同id
    private String agreementId;

    //合同id
    private String serviceHour;

    //是否分配
    private boolean isAssign = false;



    private String countDate;

    private String countMoney;

    public String getCountDate() {
        return countDate;
    }

    public void setCountDate(String countDate) {
        this.countDate = countDate;
    }

    public String getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(String countMoney) {
        this.countMoney = countMoney;
    }

    public Order(){

    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getServiceHour() {
        return serviceHour;
    }

    public void setServiceHour(String serviceHour) {
        this.serviceHour = serviceHour;
    }

    public List<OrderProfile> getOrderProfiles() {
        return orderProfiles;
    }

    public void setOrderProfiles(List<OrderProfile> orderProfiles) {
        this.orderProfiles = orderProfiles;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public Double getAliPay() {
        return aliPay;
    }

    public void setAliPay(Double aliPay) {
        this.aliPay = aliPay;
    }

    public Double getWecharPay() {
        return wecharPay;
    }

    public void setWecharPay(Double wecharPay) {
        this.wecharPay = wecharPay;
    }

//    public List<Profile> getProfiles() {
//        return profiles;
//    }
//
//    public void setProfiles(List<Profile> profiles) {
//        this.profiles = profiles;
//        if(null != profiles && profiles.size() > 0){
//            this.isAssign = true;
//        }
//    }


    public List<OrderProfile> getAdvisors() {
        return advisors;
    }

    public void setAdvisors(List<OrderProfile> advisors) {
        this.advisors = advisors;
        if (null != advisors && advisors.size() > 0) {
            this.isAssign = true;
        }
    }

    public List<OrderProfile> getUpdateOrderProfiles() {
        return updateOrderProfiles;
    }

    public void setUpdateOrderProfiles(List<OrderProfile> updateOrderProfiles) {
        this.updateOrderProfiles = updateOrderProfiles;
    }

    public String getClientHeadImgUrl() {
        return clientHeadImgUrl;
    }

    public void setClientHeadImgUrl(String clientHeadImgUrl) {
        this.clientHeadImgUrl = clientHeadImgUrl;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getWechatNum() {
        return wechatNum;
    }

    public void setWechatNum(String wechatNum) {
        this.wechatNum = wechatNum;
    }

    public Profile getSeller() {
        return seller;
    }

    public void setSeller(Profile seller) {
        this.seller = seller;
    }

    public Double getPayPalPay() {
        return payPalPay;
    }

    public void setPayPalPay(Double payPalPay) {
        this.payPalPay = payPalPay;
    }

    public Double getBankPay() {
        return bankPay;
    }

    public void setBankPay(Double bankPay) {
        this.bankPay = bankPay;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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
}
