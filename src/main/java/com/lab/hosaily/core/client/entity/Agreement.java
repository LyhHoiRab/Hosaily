package com.lab.hosaily.core.client.entity;

import com.lab.hosaily.core.client.consts.AgreementState;
import com.lab.hosaily.core.product.entity.Service;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 协议
 */
@Getter
@Setter
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
    //失效时间
    private Date deadline;
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
    private Double duration;
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
}
