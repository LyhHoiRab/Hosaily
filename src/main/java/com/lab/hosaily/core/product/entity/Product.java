package com.lab.hosaily.core.product.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Product implements Create, Update{

    //ID
    private String id;
    //企业ID
    private String organizationId;
    //名称
    private String name;
    //价格
    private Double price;
    //有效期(月)
    private Double duration;
    //服务
    private List<Service> services;
    //状态
    private UsingState state;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
