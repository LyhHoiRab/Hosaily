package com.lab.hosaily.core.im.consts;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum WechatMessageType {

    @SerializedName("1")
    TEXT("1", "文本"),

    @SerializedName("3")
    IMAGE("3", "图片"),

    @SerializedName("34")
    VOICE("34", "语音"),

    @SerializedName("43")
    VIDEO("43", "视频"),

    @SerializedName("42")
    PERSON_CARD("42", "个人名片"),

    @SerializedName("47")
    EMOTICONS("47", "动画图片"),

    @SerializedName("49")
    SHARE("49", "分享"),

    @SerializedName("50")
    ONLINE_TALK("50", "视频/语音聊天"),

    @SerializedName("10000")
    SYSTEM("10000", "系统消息"),

    @SerializedName("436207665")
    LUCKY_PACKAGE("436207665", "收到红包"),

    @SerializedName("436207668")
    LUCKY_PACKAGE_SEND("436207668", "发红包"),

    @SerializedName("419430449")
    TRANSFER("419430449", "收到转账"),

    @SerializedName("2000")
    TRANSFER_CONFIRM("2000", "确认转账"),

    @SerializedName("2001")
    TRANSFER_SEND("2001", "发转账"),

    @SerializedName("419430452")
    TRANSFER_DRAW("419430452", "转账被领取"),

    @SerializedName("55535")
    LUCKY_PACKAGE_TRANSFER_CONFIRM("55535", "确认红包"),

    @SerializedName("100")
    NEW_FRIEND("100", "新增好友"),

    @SerializedName("101")
    DELETE_FRIEND("101", "删除好友"),

    @SerializedName("102")
    MANUAL_NEW_FRIEND("102", "手动通过好友申请"),

    @SerializedName("103")
    FRIEND_ADVICE("103", "好友申请"),

    @SerializedName("116")
    SHOW_WXNO("116", "显示微信号开关"),

    @SerializedName("117")
    SCAN("117", "微信扫一扫开关"),

    @SerializedName("118")
    PASS_NEW_FRIEND_SWITCH("118", "好友通过开关"),

    @SerializedName("119")
    LUCKY_PACKAGE_SWITCH("119", "红包开关"),

    @SerializedName("200")
    MOMENTS("200", "朋友圈"),

    @SerializedName("201")
    ADDCONTACTS("201", "添加好友"),

    @SerializedName("202")
    WECHAT_SWITCH("202", "微信权限开关"),

    @SerializedName("203")
    APP_UPGRADE("203", "APP更新"),

    @SerializedName("205")
    WORD_CHANGE("205", "敏感词更新"),

    @SerializedName("300")
    RETRACT("300", "撤回"),

    @SerializedName("999")
    UNKNOWN("999", "未知");

    private String id;
    private String description;

    public static WechatMessageType getById(String id){
        if(StringUtils.isBlank(id)){
            return UNKNOWN;
        }

        for(WechatMessageType type : WechatMessageType.values()){
            if(type.id.equals(id)){
                return type;
            }
        }

        return UNKNOWN;
    }
}
