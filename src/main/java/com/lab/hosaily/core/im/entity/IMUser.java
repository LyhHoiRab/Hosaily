package com.lab.hosaily.core.im.entity;

import com.lab.hosaily.core.im.consts.IMUserType;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class IMUser  implements Create, Update {
    private String id;
    private String relationId;
    private String name;
    private String nickname;
    private String headImgUrl;
    private String sig;
    private String sdkAppId;
    private IMUserType type;
    private Date createTime;
    private Date updateTime;
}
