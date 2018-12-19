package com.lab.hosaily.core.im.entity;


import com.lab.hosaily.core.im.consts.WechatMessageType;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class IMCustomMessage{

    private String id;
    //客户微信号
    private String  wxno;
    //微信ID
    private String  wxid;
    //发送者
    private String  fromAccount;
    //接收者
    private String  toAccount;
    //消息正文
    private String  content;
    //发送时间
    private Date    conversationTime;
    //消息类型
    private WechatMessageType type;

    private Date   createTime;
    private Date   updateTime;
}
