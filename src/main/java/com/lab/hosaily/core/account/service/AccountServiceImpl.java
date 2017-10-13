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

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService{

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

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
    public void registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv){
        try{
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "小程序Token不能为空");
            Assert.hasText(signature, "校验签名不能为空");
            Assert.hasText(rawData, "签名明文不能为空");
            Assert.hasText(encryptedData, "用户加密信息不能为空");
            Assert.hasText(iv, "加密偏移量不能为空");

            //查询小程序应用
            Application application = applicationDao.getByToken(token);
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
            //unionId
            String unionId = response.getUnionId();

            //验签
            String sign = SHAUtils.bySHA1(rawData + sessionKey);
            //验签成功
            if(sign.equals(signature)){
                //解密用户信息
                XcxAccount decrypt = XcxUtils.decrypt(encryptedData, sessionKey, iv);
                //校验水印
                if(decrypt.getWatermark().get("openid").equals(appId)){
                    XcxAccount xcxAccount = xcxAccountDao.getByOpenId(decrypt.getOpenId());
                    if(xcxAccount == null){
                        //未注册
                        xcxAccount = decrypt;
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
                    }else if(!account.getWeChat().equals(weChat)){
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
                }
            }
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
    public Account registerByWeb(String token, String code){
        try{
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "网站应用Token不能为空");

            //查询网站应用
            Application application = applicationDao.getByToken(token);
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

            String weChat = StringUtils.isBlank(accessToken.getUnionId()) ? accessToken.getOpenId() : accessToken.getUnionId();
            Account account = accountDao.getByOpenIdOrUnionId(accessToken.getOpenId(), accessToken.getUnionId());
            if(account == null){
                //未注册
                account = new Account();
                account.setWeChat(weChat);
                accountDao.saveOrUpdate(account);
            }else if(!account.getWeChat().equals(weChat)){
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

            return account;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
