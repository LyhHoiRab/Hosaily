package com.lab.hosaily.core.app.dao;


import com.lab.hosaily.core.app.dao.mapper.InviteWeChatAccountMapper;
import com.lab.hosaily.core.app.entity.InviteWeChatAccount;
import com.lab.hosaily.core.app.entity.Poster;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class InviteWeChatAccountDao {

    private static Logger logger = LoggerFactory.getLogger(InviteWeChatAccountDao.class);

    @Autowired
    private InviteWeChatAccountMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(InviteWeChatAccount account){
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
    public InviteWeChatAccount getById(String id){
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
    public InviteWeChatAccount getByOpenId(String openId){
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
    public InviteWeChatAccount getByAccountIdAndAppId(String accountId, String appId){
        try{
            Assert.hasText(accountId, "账户Id不能为空");
            Assert.hasText(appId, "AppId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.id", accountId));
            criteria.and(Restrictions.eq("w.appId", appId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<InviteWeChatAccount> page(PageRequest pageRequest, String sellerId, String advisorId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.desc("w.createTime"));
            if(!StringUtils.isBlank(sellerId)){
                criteria.and(Restrictions.like("w.sellerId", sellerId));
            }
            if(!StringUtils.isBlank(advisorId)){
                criteria.and(Restrictions.like("w.advisorId", advisorId));
            }
            Long count = mapper.countByParams(criteria);
            List<InviteWeChatAccount> list = mapper.findByParams(criteria);

            return new Page<InviteWeChatAccount>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
