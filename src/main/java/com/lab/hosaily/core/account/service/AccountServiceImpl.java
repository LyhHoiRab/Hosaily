package com.lab.hosaily.core.account.service;

import com.lab.hosaily.commons.response.wechat.AccessTokenResponse;
import com.lab.hosaily.commons.response.wechat.SessionKeyResponse;
import com.lab.hosaily.commons.utils.WeChatUtils;
import com.lab.hosaily.commons.utils.XcxUtils;
import com.lab.hosaily.core.account.dao.AccountDao;
import com.lab.hosaily.core.account.entity.XcxAccount;
import com.lab.hosaily.core.application.dao.ApplicationDao;
import com.lab.hosaily.core.application.entity.Application;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.MD5Utils;
import com.rab.babylon.commons.utils.SHAUtils;
import com.rab.babylon.core.account.entity.Account;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService{

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ApplicationDao applicationDao;

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
                XcxAccount xcxAccount = XcxUtils.decrypt(encryptedData, sessionKey, iv);
                //校验水印
                if(xcxAccount.getWatermark().get("openid").equals(appId)){
                    String weChat = StringUtils.isBlank(unionId) ? openId : unionId;

                    Account account = accountDao.getByWeChat(weChat);
                    //未注册
                    if(account == null){
                        account = new Account();
                        account.setWeChat(weChat);
                        //设置默认密码
                        account.setPassword(MD5Utils.encrypt("kuliao5201314"));
                        accountDao.saveOrUpdate(account);
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
    public void registerByWeb(String token, String code){
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
                //TODO 查询opneId失败
            }

            String weChat = StringUtils.isBlank(accessToken.getUnionId()) ? accessToken.getOpenId() : accessToken.getUnionId();
            Account account = accountDao.getByWeChat(weChat);
            //未注册
            if(account == null){
                account = new Account();
                account.setWeChat(weChat);
                //设置默认密码
                account.setPassword(MD5Utils.encrypt("kuliao5201314"));
                accountDao.saveOrUpdate(account);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 公众账号注册
     */
    @Override
    @Transactional(readOnly = false)
    public void registerByWeChat(String weChat){
        try{
            Assert.hasText(weChat, "用户微信帐号不能为空");

            Account account = accountDao.getByWeChat(weChat);
            //未注册
            if(account == null){
                account = new Account();
                account.setWeChat(weChat);
                //设置默认密码
                account.setPassword(MD5Utils.encrypt("kuliao5201314"));
                accountDao.saveOrUpdate(account);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
