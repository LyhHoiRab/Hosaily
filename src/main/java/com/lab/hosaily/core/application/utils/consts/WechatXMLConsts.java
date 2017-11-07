package com.lab.hosaily.core.application.utils.consts;

/**
 * 微信XML节点常量
 */
public class WechatXMLConsts{

    //节点名称
    //加密节点
    public final static String NODE_ENCRYPT = "Encrypt";
    //收件人
    public final static String NODE_TO_USER_NAME = "ToUserName";
    //发件人
    public final static String NODE_FROM_USER_NAME = "FromUserName";
    //信息类型
    public final static String NODE_MSG_TYPE = "MsgType";
    //事件类型
    public final static String NODE_EVENT = "Event";

    //信息类型
    //事件消息
    public final static String MSG_TYPE_EVENT = "event";
    //文本消息
    public final static String MSG_TYPE_TEXT = "text";
    //图片消息
    public final static String MSG_TYPE_IMG = "image";
    //语音消息
    public final static String MSG_TYPE_VOICE = "voice";
    //视频消息
    public final static String MSG_TYPE_VIDEO = "video";
    //小视频消息
    public final static String MSG_TYPE_SHORTVIDEO = "shortvideo";
    //地理位置消息
    public final static String MSG_TYPE_LOCATION = "location";
    //链接消息
    public final static String MSG_TYPE_LINK = "link";

    //事件类型
    //关注事件
    public final static String EVENT_TYPE_SUBSCRIBE = "subscribe";
    //取消关注事件
    public final static String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    //扫码事件
    public final static String EVENT_TYPE_SCAN = "SCAN";
    //上报地理位置事件
    public final static String EVENT_TYP_LOCATION = "LOCATION";
    //菜单点击事件
    public final static String EVENT_TYPE_CLICK = "CLICK";
    //菜单跳转链接事件
    public final static String EVENT_TYPE_VIEW = "VIEW";
}
