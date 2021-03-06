package com.lab.hosaily.core.im.entity;

import com.lab.hosaily.core.im.consts.IMMessageType;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;
import java.util.List;

/**
 * @link https://cloud.tencent.com/document/product/269/2282
 */
@Getter
@Setter
public class IMMessage {

    private String id;

    @SerializedName("SyncOtherMachine")
    private Integer syncOtherMachine;

    @SerializedName("From_Account")
    private String fromAccount;

    @SerializedName("To_Account")
    private String toAccount;

    @SerializedName("MsgLifeTime")
    private Integer msgLifeTime;

    @SerializedName("MsgRandom")
    private Long msgRandom;

    @SerializedName("MsgTimeStamp")
    private Long msgTimeStamp;

    @SerializedName("MsgBody")
    private List<IMMsgBody> msgBody;

    @SerializedName("MsgType")
    private IMMessageType type;

    private Date createTime;
    private Date updateTime;
}
