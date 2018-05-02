package com.lab.hosaily.core.client.entity;

import com.lab.hosaily.core.client.consts.AppointmentState;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.Sex;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Appointment implements Create, Update{

    private String id;
    private String organizationId;
    private String name;
    private String wechat;
    private Sex sex;
    private String phone;
    private String type;
    private String description;
    private String url;
    private AppointmentState state;
    private String remark;
    private Date createTime;
    private Date updateTime;
}
