package com.lab.hosaily.core.client.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Contract implements Create, Update{

    private String id;
    private Integer version;
    private List<Regulation> regulations;
    private Date createTime;
    private Date updateTime;
}
