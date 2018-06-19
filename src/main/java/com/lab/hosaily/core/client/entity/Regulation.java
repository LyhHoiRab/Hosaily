package com.lab.hosaily.core.client.entity;

import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Regulation implements Create, Update{

    private String  id;
    private String  parentId;
    private String  contractId;
    private String  title;
    private String  content;
    private Integer levelUp;
    private Integer sort;
    private Date    createTime;
    private Date    updateTime;

    private List<Regulation> children;
}
