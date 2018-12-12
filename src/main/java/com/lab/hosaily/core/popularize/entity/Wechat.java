package com.lab.hosaily.core.popularize.entity;

import com.lab.hosaily.core.course.entity.Advisor;
import com.rab.babylon.core.base.entity.Create;
import com.rab.babylon.core.base.entity.Update;
import com.rab.babylon.core.consts.entity.UsingState;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Getter
@Setter
public class Wechat implements Create, Update{

    private String     id;
    private String     wxno;
    private String     headImgUrl;
    private String     nickname;
    private String     advisorId;
    private String     organizationId;
    private String     remark;
    private String     seller;
    private String     qr;
    private UsingState state;
    private Date       createTime;
    private Date       updateTime;

    private Advisor advisor;

    @Override
    public boolean equals(Object object){
        if(object == this){
            return true;
        }

        if(object != null && this.getClass() == object.getClass()){
            Wechat entity = (Wechat) object;

            if(StringUtils.isNotBlank(entity.id) && StringUtils.isNotBlank(this.id)){
                return (entity.id.equals(this.id));

            }else{
                return entity.organizationId.equals(this.organizationId) && entity.wxno.equals(this.organizationId);
            }
        }

        return false;
    }

    public int hashCode(){
        return !StringUtils.isBlank(this.id) ? this.id.hashCode() : 0;
    }
}
