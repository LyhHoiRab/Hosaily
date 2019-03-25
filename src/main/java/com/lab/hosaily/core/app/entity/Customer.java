package com.lab.hosaily.core.app.entity;

import com.rab.babylon.core.consts.entity.Sex;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.wah.doraemon.domain.Createable;
import org.wah.doraemon.domain.Entity;
import org.wah.doraemon.domain.Updateable;


import java.util.Date;


@Getter
@Setter
public class Customer extends Entity implements Createable, Updateable {
    private Date createTime;                  //添加时间
    private Date updateTime;                  //修改时间

//    private String id;
    private String name;                      //客户名称
    private Sex sex;                      //性别
    private String phone;                      //电话
    private String weChat;                      //微信
    private Date time;                      //日期时间
    private Date processTime;               //处理时间
    private String link;                      //链接
    private String follower;                      //跟进人
    private String followerName;                  //跟进人名字（仅做显示）
    private String address;                      //地区
    private String chatRecord;                      //聊天记录
    private String situation;                      //跟进情况(0:未处理/1:处理中)
    private String qq;                      //qq
    private String uploader;                      //录入人
    private String process;                      //过程
    private String comment;                      //备注
    private String channel;                       //渠道

    private String sort;                       //渠道
    private int index;                      //临时序号


//    private String status;                      //跟进情况


//    @Override
//    public boolean equals(Object object){
//        if(this == object){
//            return true;
//        }
//
//        if(object != null && this.getClass() == object.getClass()){
//            Customer entity = (Customer) object;
//
//            if(StringUtils.isNotBlank(this.getId()) && StringUtils.isNotBlank(entity.getId())){
//                return this.getId().equals(entity.getId());
//
//            }else if(StringUtils.isNotBlank(this.phone)
//                    && StringUtils.isNotBlank(entity.phone)
//                    && StringUtils.isNotBlank(this.companyId)
//                    && StringUtils.isNotBlank(entity.companyId)){
//
//                return (this.phone.equals(entity.phone) && this.companyId.equals(entity.companyId));
//            }
//        }
//
//        return false;
//    }
}
