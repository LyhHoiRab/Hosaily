package com.lab.hosaily.core.account.service;

import com.lab.hosaily.commons.response.wechat.AccessTokenResponse;
import com.lab.hosaily.commons.response.wechat.SessionKeyResponse;
import com.lab.hosaily.commons.response.wechat.UserInfoResponse;
import com.lab.hosaily.commons.utils.WeChatUtils;
import com.lab.hosaily.commons.utils.XcxUtils;
import com.lab.hosaily.core.account.dao.AccountDao;
import com.lab.hosaily.core.account.dao.UserDao;
import com.lab.hosaily.core.account.dao.WeChatAccountDao;
import com.lab.hosaily.core.account.dao.XcxAccountDao;
import com.lab.hosaily.core.account.entity.WeChatAccount;
import com.lab.hosaily.core.account.entity.XcxAccount;
import com.lab.hosaily.core.application.dao.ApplicationDao;
import com.lab.hosaily.core.application.entity.Application;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.SHAUtils;
import com.rab.babylon.core.account.entity.Account;
import com.rab.babylon.core.account.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import redis.clients.jedis.ShardedJedisPool;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService{

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private XcxAccountDao xcxAccountDao;

    @Autowired
    private WeChatAccountDao weChatAccountDao;

    /**
     * 小程序注册
     */
    @Override
    @Transactional(readOnly = false)
    public User registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv){
        try{
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "小程序Token不能为空");
            Assert.hasText(signature, "校验签名不能为空");
            Assert.hasText(rawData, "签名明文不能为空");
            Assert.hasText(encryptedData, "用户加密信息不能为空");
            Assert.hasText(iv, "加密偏移量不能为空");

            //查询小程序应用
            Application application = applicationDao.getByToken(token);

            if(application == null){
                throw new IllegalArgumentException("无效的应用Token");
            }

            //appId
            String appId = application.getAppId();
            //secret
            String secret = application.getSecret();

            //查询sessionKey
            SessionKeyResponse response = XcxUtils.getOpenIdAndSessionKey(appId, secret, code);
            //sessionKey
            String sessionKey = response.getSessionKey();
            //openId
            String openId = response.getOpenId();

            //验签
            String sign = SHAUtils.bySHA1(rawData + sessionKey);
            //验签成功
            if(sign.equalsIgnoreCase(signature)){
                //解密用户信息
                XcxAccount decrypt = XcxUtils.decrypt(encryptedData, sessionKey, iv);
                //校验水印
                if(decrypt.getWatermark().get("appid").equals(appId) && decrypt.getOpenId().equals(openId)){
                    XcxAccount xcxAccount = xcxAccountDao.getByOpenId(decrypt.getOpenId());
                    if(xcxAccount == null){
                        //未注册
                        xcxAccount = decrypt;
                        xcxAccount.setAppId(appId);
                        xcxAccountDao.saveOrUpdate(xcxAccount);
                    }
                    //更新unionId
                    if(!StringUtils.isBlank(decrypt.getUnionId()) && !decrypt.getUnionId().equals(xcxAccount.getUnionId())){
                        xcxAccount.setUnionId(decrypt.getUnionId());
                        xcxAccountDao.saveOrUpdate(xcxAccount);
                    }

                    String weChat = StringUtils.isBlank(xcxAccount.getUnionId()) ? xcxAccount.getOpenId() : xcxAccount.getUnionId();
                    Account account = accountDao.getByOpenIdOrUnionId(xcxAccount.getOpenId(), xcxAccount.getUnionId());
                    if(account == null){
                        //未注册
                        account = new Account();
                        account.setWeChat(weChat);
                        accountDao.saveOrUpdate(account);
                    }
                    if(!account.getWeChat().equals(weChat)){
                        account.setWeChat(weChat);
                        accountDao.saveOrUpdate(account);
                    }

                    //用户信息
                    User user = userDao.getByAccountId(account.getId());
                    if(user == null){
                        //未注册
                        user = XcxUtils.changeToUser(xcxAccount);
                        user.setAccountId(account.getId());
                        userDao.saveOrUpdate(user);
                    }

                    //缓存用户信息
                    user.setSessionKey(sessionKey);
                    userDao.cache(user);
                    return user;
                }

                throw new ServiceException("用户信息水印认证失败");
            }

            throw new ServiceException("小程序注册签名认证失败");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 网站应用注册
     */
    @Override
    @Transactional(readOnly = false)
    public User registerByWeb(String token, String code){
        try{
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "网站应用Token不能为空");

            //查询网站应用
            Application application = applicationDao.getByToken(token);

            if(application == null){
                throw new IllegalArgumentException("无效的应用Token");
            }

            //appId
            String appId = application.getAppId();
            //secret
            String secret = application.getSecret();

            //查询accessToken
            AccessTokenResponse accessToken = WeChatUtils.getAccessToken(code, appId, secret);

            if(StringUtils.isBlank(accessToken.getOpenId()) && StringUtils.isBlank(accessToken.getUnionId())){
                throw new ServiceException("获取openId失败");
            }

            WeChatAccount weChatAccount = weChatAccountDao.getByOpenId(accessToken.getOpenId());
            if(weChatAccount == null){
                UserInfoResponse userInfo = WeChatUtils.getUserInfo(accessToken.getAccessToken(), appId);
                weChatAccount = userInfo.changeToWeChatAccount();
                weChatAccount.setAppId(appId);
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }
            //更新unionId
            if(!StringUtils.isBlank(accessToken.getUnionId()) && !accessToken.getUnionId().equals(weChatAccount.getUnionId())){
                weChatAccount.setUnionId(accessToken.getUnionId());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }

            String weChat = StringUtils.isBlank(accessToken.getUnionId()) ? accessToken.getOpenId() : accessToken.getUnionId();
            Account account = accountDao.getByOpenIdOrUnionId(accessToken.getOpenId(), accessToken.getUnionId());
            if(account == null){
                //未注册
                account = new Account();
                account.setWeChat(weChat);
                accountDao.saveOrUpdate(account);
            }
            //更新unionId
            if(!account.getWeChat().equals(weChat)){
                account.setWeChat(weChat);
                accountDao.saveOrUpdate(account);
            }

            //用户信息
            User user = userDao.getByAccountId(account.getId());
            if(user == null){
                //未注册
                user = WeChatUtils.changeToUser(weChatAccount);
                user.setAccountId(account.getId());
                userDao.saveOrUpdate(user);
            }

            //缓存用户信息
            userDao.cache(user);

            return user;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 小程序获取用户电话
     */
    @Override
    @Transactional(readOnly = false)
    public String phoneByXcx(String token, String accountId, String sessionKey, String encryptedData, String iv){
        try{
            Assert.hasText(accountId, "账户ID不能为空");
            Assert.hasText(encryptedData, "用户加密信息不能为空");
            Assert.hasText(iv, "加密偏移量不能为空");

            //查询小程序应用
            Application application = applicationDao.getByToken(token);
            //appId
            String appId = application.getAppId();
//            //secret
//            String secret = application.getSecret();

//            //查询sessionKey
//            SessionKeyResponse response = XcxUtils.getOpenIdAndSessionKey(appId, secret, code);
//            //sessionKey
//            String sessionKey = response.getSessionKey();
//            //openId
//            String openId = response.getOpenId();
//            //unionId
//            String unionId = response.getUnionId();

            //解密用户信息
            XcxAccount decrypt = XcxUtils.decrypt(encryptedData, sessionKey, iv);

            //校验水印
            if(decrypt.getWatermark().get("appid").equals(appId)){
                Account account = accountDao.getById(accountId);
                if(account != null){
                    account.setPhone(decrypt.getPurePhoneNumber());
                    accountDao.saveOrUpdate(account);

                    //更新小程序用户信息
                    XcxAccount xcxAccount = xcxAccountDao.getByOpenIdOrUnionId(account.getWeChat(), account.getWeChat());
                    if(xcxAccount != null){
                        xcxAccount.setPhoneNumber(decrypt.getPhoneNumber());
                        xcxAccount.setPurePhoneNumber(decrypt.getPurePhoneNumber());
                        xcxAccount.setCountryCode(decrypt.getCountryCode());
                        xcxAccountDao.saveOrUpdate(xcxAccount);
                    }

                    //更新用户信息
                    User user = userDao.getByAccountId(account.getId());
                    if(user != null){
                        user.setPhone(decrypt.getPurePhoneNumber());
                        userDao.saveOrUpdate(user);
                    }

                    return decrypt.getPhoneNumber();
                }
            }

            throw new ServiceException("用户信息水印认证失败");
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
