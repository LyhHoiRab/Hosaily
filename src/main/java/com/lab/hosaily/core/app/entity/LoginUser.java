package com.lab.hosaily.core.app.entity;

import com.rab.babylon.core.consts.entity.Sex;
import lombok.Getter;
import lombok.Setter;
import org.wah.doraemon.domain.Createable;
import org.wah.doraemon.domain.Entity;
import org.wah.doraemon.domain.Updateable;

import java.util.Date;


@Getter
@Setter
public class LoginUser extends Entity implements Createable, Updateable {
    private Date createTime;                  //添加时间
    private Date updateTime;                  //修改时间

    private String phone;                      //客户电话号码（唯一）
    private String role;                      //角色    0:超级管理员/1:客服（拥有查看自己客户的权限）/2:普通用户可以查看全部（但是隐藏微信号和电话好中间几位）
    private String name;                      //客户名称
}
