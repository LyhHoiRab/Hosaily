package com.lab.hosaily.core.account.dao;

import com.lab.hosaily.core.account.dao.mapper.WeChatAccountMapper;
import com.lab.hosaily.core.account.entity.WeChatAccount;
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

import javax.xml.crypto.Data;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

@Repository
public class WeChatAccountDao{

    private static Logger logger = LoggerFactory.getLogger(WeChatAccountDao.class);

    @Autowired
    private WeChatAccountMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(WeChatAccount account){
        try{
            Assert.notNull(account, "微信账户信息不能为空");

            if(StringUtils.isBlank(account.getId())){
                Assert.hasText(account.getOpenId(), "微信账户openId不能为空");
                Assert.hasText(account.getAppId(), "微信账户AppId不能为空");

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
     * 根据ID查询
     */
    public WeChatAccount getById(String id){
        try{
            Assert.hasText(id, "微信账户ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据openId查询
     */
    public WeChatAccount getByOpenId(String openId){
        try{
            Assert.hasText(openId, "微信账户openId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.openId", openId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID和appId查询
     */
    public WeChatAccount getByAccountIdAndAppId(String accountId, String appId){
        try{
            Assert.hasText(accountId, "账户Id不能为空");
            Assert.hasText(appId, "AppId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.accountId", accountId));
            criteria.and(Restrictions.eq("w.appId", appId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
