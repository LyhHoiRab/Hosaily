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

    private String phone;                      //客户名称
    private String role;                      //客户名称
    private String name;                      //客户名称
}
