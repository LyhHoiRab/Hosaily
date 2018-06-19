package com.lab.hosaily.core.client.entity;

import com.lab.hosaily.core.client.consts.AgreementState;
import com.lab.hosaily.core.product.entity.Service;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;

import java.util.Date;
import java.util.List;

/**
 * 协议
 */
public class Agreement implements Create, Update{

    //ID
    private String id;
    //购买记录ID
    private String purchaseId;
    //销售ID
    private String serviceId;
    //客户ID
    private String accountId;
    //客户名称
    private String client;
    //联系电话
    private String phone;
    //地址
    private String address;
    //身份证
    private String idCard;
    //微信
    private String wechat;
    //邮箱
    private String email;
    //紧急联系人
    private String emergencyContact;
    //公司名称
    private String company;
    //营业执照注册号
    private String licenseNumber;
    //法定代表人
    private String legalPerson;
    //公司地址
    private String companyAddress;
    //公司联系电话
    private String companyPhone;
    //公司邮箱
    private String companyEmail;
    //公司网址
    private String companyWebsite;
    //确认时间
    private Date affirmTime;
    //版本(预留)
    private Integer version;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //服务
    private List<Service> services;
    //套餐名称
    private String name;
    //产品价格
    private Double retail;
    //售价
    private Double price;
    //有效期(月)
    private Integer duration;
    //已付金额
    private Double paid;
    //签名图片路径
    private String autographUrl;
    //状态
    private AgreementState state;
    //是否可修改
    private Boolean edit;
    //客户头像
    private String clientHeadImgUrl;
    //客户微信名称
    private String clientNickname;
    //合同
    private Contract contract;

    public Agreement(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public Date getAffirmTime() {
        return affirmTime;
    }

    public void setAffirmTime(Date affirmTime) {
        this.affirmTime = affirmTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public AgreementState getState() {
        return state;
    }

    public void setState(AgreementState state) {
        this.state = state;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public Double getPaid(){
        return paid;
    }

    public void setPaid(Double paid){
        this.paid = paid;
    }

    public String getAutographUrl() {
        return autographUrl;
    }

    public void setAutographUrl(String autographUrl) {
        this.autographUrl = autographUrl;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientHeadImgUrl() {
        return clientHeadImgUrl;
    }

    public void setClientHeadImgUrl(String clientHeadImgUrl) {
        this.clientHeadImgUrl = clientHeadImgUrl;
    }

    public String getClientNickname() {
        return clientNickname;
    }

    public void setClientNickname(String clientNickname) {
        this.clientNickname = clientNickname;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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
