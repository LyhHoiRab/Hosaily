package com.lab.hosaily.core.account.dao;

import com.lab.hosaily.core.account.dao.mapper.AppAccountMapper;
import com.lab.hosaily.core.account.entity.AppAccount;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;

@Repository
public class AppAccountDao {

    private static Logger logger = LoggerFactory.getLogger(AppAccountDao.class);

    @Autowired
    private AppAccountMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(AppAccount account){
        try{
            Assert.notNull(account, "小程序账户信息不能为空");

            if(StringUtils.isBlank(account.getId())){
                Assert.hasText(account.getOpenId(), "小程序账户OpenId不能为空");

                account.setId(UUIDGenerator.by32());
                account.setState(UsingState.NORMAL);
                account.setCreateTime(new Date());
                mapper.save(account);
            }else{
                account.setUpdateTime(new Date());
                mapper.update(account);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询记录
     */
    public AppAccount getById(String id){
        try{
            Assert.hasText(id, "小程序ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据openId查询记录
     */
    public AppAccount getByOpenId(String openId){
        try{
            Assert.hasText(openId, "小程序openId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("openId", openId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据openId或unionId查询记录
     */
    public AppAccount getByOpenIdOrUnionId(String appId, String openId, String unionId){
        try{
            Assert.hasText(appId, "小程序AppId不能为空");
            Assert.hasText(openId, "openId不能为空");
            Assert.hasText(unionId, "unionId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("appId", appId));
            criteria.and(Restrictions.or(Restrictions.eq("unionId", unionId), Restrictions.eq("openId", openId)));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
