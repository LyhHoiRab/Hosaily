package com.lab.hosaily.core.im.dao;

import com.lab.hosaily.core.im.consts.IMUserType;
import com.lab.hosaily.core.im.dao.mapper.IMUserMapper;
import com.lab.hosaily.core.im.entity.IMUser;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;


import java.util.Date;

@Repository
public class IMUserDao{

    private Logger logger = LoggerFactory.getLogger(IMUserDao.class);

    @Autowired
    private IMUserMapper mapper;

    public void saveOrUpdate(IMUser user){
        try{
            Assert.notNull(user, "IM用户信息不能为空");

            if(StringUtils.isBlank(user.getId())){
                Assert.hasText(user.getRelationId(), "IM用户关联ID不能为空");
                Assert.notNull(user.getType(), "IM用户类型不能为空");
                Assert.hasText(user.getSig(), "IM用户签名不能为空");
                Assert.hasText(user.getSdkAppId(), "IM用户AppId不能为空");

                user.setId(UUIDGenerator.by32());
                user.setCreateTime(new Date());
                mapper.save(user);
            }else{
                user.setUpdateTime(new Date());
                mapper.update(user);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public IMUser getByRelationIdAndType(String relationId, IMUserType type){
        try{
            Assert.hasText(relationId, "IM用户关联ID不能为空");
            Assert.notNull(type, "IM用户类型不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("relationId", relationId));
            criteria.and(Restrictions.eq("type", type.getId()));

            return mapper.get(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
