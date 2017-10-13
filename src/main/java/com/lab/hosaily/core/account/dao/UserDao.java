package com.lab.hosaily.core.account.dao;

import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.core.account.dao.mapper.UserMapper;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.RedisUtils;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Date;

@Repository
public class UserDao{

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private UserMapper mapper;

    @Autowired
    private ShardedJedisPool jedisPool;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(User user){
        try{
            Assert.notNull(user, "用户信息不能为空");

            if(StringUtils.isBlank(user.getId())){
                Assert.hasText(user.getAccountId(), "账户ID不能为空");

                user.setId(UUIDGenerator.by32());
                user.setIsDelete(false);
                user.setState(UsingState.NORMAL);
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

    /**
     * 根据ID查询
     */
    public User getById(String id){
        try{
            Assert.hasText(id, "用户信息ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("u.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据账户ID查询
     */
    public User getByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户信息ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("u.accountId", accountId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 缓存用户信息
     */
    public void cache(User user){
        try{
            Assert.notNull(user, "用户信息不能为空");
            Assert.hasText(user.getId(), "用户ID不能为空");
            Assert.hasText(user.getAccountId(), "账户ID不能为空");

            ShardedJedis jedis = jedisPool.getResource();
            RedisUtils.save(jedis, RedisConsts.USER_INFO + user.getAccountId(), user, RedisConsts.USER_EFFECTIVE_SECOND);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据accountId查询用户缓存信息
     */
    public User getCacheByAccountId(String accountId){
        try{
            Assert.hasText(accountId, "账户ID不能为空");

            ShardedJedis jedis = jedisPool.getResource();
            User user = RedisUtils.get(jedis, RedisConsts.USER_INFO + accountId, User.class);

            //缓存没有
            if(user == null){
                user = getByAccountId(accountId);
                cache(user);
            }

            return user;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
