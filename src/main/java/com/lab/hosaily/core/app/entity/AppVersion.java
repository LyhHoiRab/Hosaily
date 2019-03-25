package com.lab.hosaily.core.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.wah.doraemon.domain.Createable;
import org.wah.doraemon.domain.Entity;
import org.wah.doraemon.domain.Updateable;

import java.util.Date;


@Getter
@Setter
public class AppVersion extends Entity implements Createable, Updateable {
    private Date createTime;                  //添加时间
    private Date updateTime;                  //修改时间

    private String version;                      //版本号，唯一
    private String des;                      //详细说明
    private String name;                      //版本名
}
