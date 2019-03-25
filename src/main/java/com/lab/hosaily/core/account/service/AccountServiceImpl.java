package com.lab.hosaily.core.account.service;

import com.lab.hosaily.commons.response.wechat.AccessTokenResponse;
import com.lab.hosaily.commons.response.wechat.SessionKeyResponse;
import com.lab.hosaily.commons.response.wechat.UserInfoResponse;
import com.lab.hosaily.commons.utils.WeChatUtils;
import com.lab.hosaily.commons.utils.XcxUtils;
import com.lab.hosaily.core.account.consts.Gender;
import com.lab.hosaily.core.account.consts.WeChatSex;
import com.lab.hosaily.core.account.dao.*;
import com.lab.hosaily.core.account.entity.AppAccount;
import com.lab.hosaily.core.account.entity.WeChatAccount;
import com.lab.hosaily.core.account.entity.XcxAccount;
import com.lab.hosaily.core.app.dao.InviteWeChatAccountDao;
import com.lab.hosaily.core.app.dao.PosterDao;
import com.lab.hosaily.core.app.dao.ProfileDao;
import com.lab.hosaily.core.app.entity.InviteWeChatAccount;
import com.lab.hosaily.core.app.entity.Profile;
import com.lab.hosaily.core.application.dao.ApplicationDao;
import com.lab.hosaily.core.application.entity.Application;
import com.lab.hosaily.core.application.utils.WechatUtils;
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

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

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
    private AppAccountDao appAccountDao;

    @Autowired
    private WeChatAccountDao weChatAccountDao;

    @Autowired
    private ProfileDao profileDao;

    @Autowired
    private InviteWeChatAccountDao inviteWeChatAccountDao;

    /**
     * 小程序注册
     */
    @Override
    @Transactional(readOnly = false)
    public User registerByXcx(String token, String code, String signature, String rawData, String encryptedData, String iv) {
        try {
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "小程序Token不能为空");
            Assert.hasText(signature, "校验签名不能为空");
            Assert.hasText(rawData, "签名明文不能为空");
            Assert.hasText(encryptedData, "用户加密信息不能为空");
            Assert.hasText(iv, "加密偏移量不能为空");

            //查询小程序应用
            Application application = applicationDao.getByToken(token);

            if (application == null) {
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
            if (sign.equalsIgnoreCase(signature)) {
                //解密用户信息
                XcxAccount decrypt = XcxUtils.decrypt(encryptedData, sessionKey, iv);
                //校验水印
                if (decrypt.getWatermark().get("appid").equals(appId) && decrypt.getOpenId().equals(openId)) {
                    XcxAccount xcxAccount = xcxAccountDao.getByOpenId(decrypt.getOpenId());
                    if (xcxAccount == null) {
                        //未注册
                        xcxAccount = decrypt;
                        xcxAccount.setAppId(appId);
                        xcxAccountDao.saveOrUpdate(xcxAccount);
                    } else {
                        //更新微信信息
                        xcxAccount.setNickname(decrypt.getNickname());
                        xcxAccount.setGender(decrypt.getGender());
                        xcxAccount.setLanguage(decrypt.getLanguage());
                        xcxAccount.setCountry(decrypt.getCountry());
                        xcxAccount.setProvince(decrypt.getProvince());
                        xcxAccount.setCity(decrypt.getCity());
                        xcxAccount.setAvatarUrl(decrypt.getAvatarUrl());
                        xcxAccountDao.saveOrUpdate(xcxAccount);
                    }
                    //更新unionId
                    if (!StringUtils.isBlank(decrypt.getUnionId()) && !decrypt.getUnionId().equals(xcxAccount.getUnionId())) {
                        xcxAccount.setUnionId(decrypt.getUnionId());
                        xcxAccountDao.saveOrUpdate(xcxAccount);
                    }

                    String weChat = StringUtils.isBlank(xcxAccount.getUnionId()) ? xcxAccount.getOpenId() : xcxAccount.getUnionId();
                    Account account = accountDao.getByOpenIdOrUnionId(xcxAccount.getOpenId(), xcxAccount.getUnionId());
                    if (account == null) {
                        //未注册
                        account = new Account();
                        account.setWeChat(weChat);
                        accountDao.saveOrUpdate(account);
                    }
                    if (!account.getWeChat().equals(weChat)) {
                        account.setWeChat(weChat);
                        accountDao.saveOrUpdate(account);
                    }

                    //用户信息
                    User user = userDao.getByAccountId(account.getId());
                    if (user == null) {
                        //未注册
                        user = XcxUtils.changeToUser(xcxAccount);
                        user.setAccountId(account.getId());
                        userDao.saveOrUpdate(user);
                    } else {
                        //更新用户信息
                        user.setNickname(xcxAccount.getNickname());
                        user.setHeadImgUrl(xcxAccount.getAvatarUrl());
                        user.setSex(Gender.changeToSex(xcxAccount.getGender()));
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 小程序注册
     */
    @Override
    @Transactional(readOnly = false)
    public User loginWeb(String phone, String password, String accountId) {
        Account account = accountDao.getById(accountId);
        User user = null;
        if (account.getPhone().equals(phone) && account.getPassword().equals(password)) {
            user = userDao.getByAccountId(accountId);
        }
        return user;
    }


    /**
     * 网站应用注册
     */
    @Override
    @Transactional(readOnly = false)
    public User registerByNewWeb(String token, String code) {
        try {
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "网站应用Token不能为空");

            //查询网站应用
            Application application = applicationDao.getByToken(token);

            if (application == null) {
                throw new IllegalArgumentException("无效的应用Token");
            }

            //appId
            String appId = application.getAppId();
            //secret
            String secret = application.getSecret();

            //查询accessToken
            AccessTokenResponse accessToken = WeChatUtils.getAccessToken(code, appId, secret);

            if (StringUtils.isBlank(accessToken.getOpenId()) && StringUtils.isBlank(accessToken.getUnionId())) {
                throw new ServiceException("获取openId失败");
            }

            WeChatAccount weChatAccount = weChatAccountDao.getByOpenId(accessToken.getOpenId());
            UserInfoResponse userInfo = WeChatUtils.getUserInfo(accessToken.getAccessToken(), appId);
            if (weChatAccount == null) {
                //未注册
                weChatAccount = userInfo.changeToWeChatAccount();
                weChatAccount.setAppId(appId);
                weChatAccountDao.saveOrUpdate(weChatAccount);
            } else {
                //更新微信信息
                weChatAccount.setNickname(userInfo.getNickname());
                weChatAccount.setSex(userInfo.getSex());
                weChatAccount.setCity(userInfo.getCity());
                weChatAccount.setProvince(userInfo.getProvince());
                weChatAccount.setCountry(userInfo.getCountry());
                weChatAccount.setHeadImgUrl(userInfo.getHeadImgUrl());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }
            //更新unionId
            if (!StringUtils.isBlank(accessToken.getUnionId()) && !accessToken.getUnionId().equals(weChatAccount.getUnionId())) {
                weChatAccount.setUnionId(accessToken.getUnionId());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }

            String weChat = StringUtils.isBlank(accessToken.getUnionId()) ? accessToken.getOpenId() : accessToken.getUnionId();
            Account account = accountDao.getByOpenIdOrUnionId(accessToken.getOpenId(), accessToken.getUnionId());
            if (account == null) {
                //未注册
                account = new Account();
                account.setWeChat(weChat);
                accountDao.saveOrUpdate(account);
            }
            //更新unionId
            if (!account.getWeChat().equals(weChat)) {
                account.setWeChat(weChat);
                accountDao.saveOrUpdate(account);
            }

            //用户信息
            User user = userDao.getByAccountId(account.getId());
            if (user == null) {
                //未注册
                user = WeChatUtils.changeToUser(weChatAccount);
                user.setAccountId(account.getId());
                userDao.saveOrUpdate(user);
            } else {
                //更新用户信息
                user.setNickname(weChatAccount.getNickname());
                user.setHeadImgUrl(weChatAccount.getHeadImgUrl());
                user.setSex(WeChatSex.changeToSex(weChatAccount.getSex()));
                userDao.saveOrUpdate(user);
            }


            Profile profile = profileDao.getByAccountId(account.getId());
            if(null == profile){
                Profile newProfile = new Profile();
                newProfile.setId(account.getId());
                newProfile.setAccountId(account.getId());
                newProfile.setName(weChatAccount.getNickname());
                newProfile.setHeadImgUrl(weChatAccount.getHeadImgUrl());
                newProfile.setSex(WeChatSex.changeToSex(weChatAccount.getSex()));
                newProfile.setNickname(weChatAccount.getNickname());
                profileDao.save(newProfile);
            }



            //缓存用户信息
            userDao.cache(user);

            return user;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }







    @Override
    @Transactional(readOnly = false)
    public Map<String, String> registerByH5Share(String token, String code, String sellerId) {
        try {
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "网站应用Token不能为空");
            Assert.hasText(sellerId, "sellerId不能为空");
//            Assert.hasText(advisorId, "advisorId不能为空");

            //查询网站应用
            Application application = applicationDao.getByToken(token);

            if (application == null) {
                throw new IllegalArgumentException("无效的应用Token");
            }

            //appId
            String appId = application.getAppId();
            //secret
            String secret = application.getSecret();

            //查询accessToken
            AccessTokenResponse accessToken = WeChatUtils.getAccessToken(code, appId, secret);

            if (StringUtils.isBlank(accessToken.getOpenId()) && StringUtils.isBlank(accessToken.getUnionId())) {
                throw new ServiceException("获取openId失败");
            }

            InviteWeChatAccount inviteWeChatAccount = inviteWeChatAccountDao.getByOpenId(accessToken.getOpenId());
            UserInfoResponse userInfo = WeChatUtils.getUserInfo(accessToken.getAccessToken(), appId);
            if (inviteWeChatAccount == null) {
                //未注册
                inviteWeChatAccount = userInfo.changeToWeChatAccountForInvite();
                inviteWeChatAccount.setAppId(appId);
                inviteWeChatAccount.setSellerId(sellerId);
//                inviteWeChatAccount.setAdvisorId(advisorId);
                inviteWeChatAccountDao.saveOrUpdate(inviteWeChatAccount);
            } else {
                //更新微信信息
                inviteWeChatAccount.setNickname(userInfo.getNickname());
                inviteWeChatAccount.setSex(userInfo.getSex());
                inviteWeChatAccount.setCity(userInfo.getCity());
                inviteWeChatAccount.setProvince(userInfo.getProvince());
                inviteWeChatAccount.setCountry(userInfo.getCountry());
                inviteWeChatAccount.setHeadImgUrl(userInfo.getHeadImgUrl());
                inviteWeChatAccountDao.saveOrUpdate(inviteWeChatAccount);
            }
            //更新unionId
            if (!StringUtils.isBlank(accessToken.getUnionId()) && !accessToken.getUnionId().equals(inviteWeChatAccount.getUnionId())) {
                inviteWeChatAccount.setUnionId(accessToken.getUnionId());
                inviteWeChatAccountDao.saveOrUpdate(inviteWeChatAccount);
            }
            Profile profile = profileDao.getById(sellerId);

            Map<String, String> simpleUser = new HashMap<String, String>();
            simpleUser.put("headImgUrl", profile.getHeadImgUrl());
            simpleUser.put("nickname", profile.getNickname());
            simpleUser.put("qrCodeImgUrl",profile.getQrCodeImgUrl());
            return simpleUser;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public User registerByApp(AppAccount appAccount) {
        try {

            AppAccount newAppAccount = appAccountDao.getByOpenId(appAccount.getOpenId());
            if (newAppAccount == null) {
                //未注册
                appAccountDao.saveOrUpdate(appAccount);
            } else {
                //更新微信信息
                newAppAccount.setNickname(appAccount.getNickname());
                newAppAccount.setGender(appAccount.getGender());
                newAppAccount.setLanguage(appAccount.getLanguage());
                newAppAccount.setCountry(appAccount.getCountry());
                newAppAccount.setProvince(appAccount.getProvince());
                newAppAccount.setCity(appAccount.getCity());
                newAppAccount.setAvatarUrl(appAccount.getAvatarUrl());
                newAppAccount.setAppId(appAccount.getAppId());
                appAccountDao.saveOrUpdate(newAppAccount);
            }

            Account account = accountDao.getByOpenIdOrUnionId(appAccount.getOpenId(), appAccount.getUnionId());
            if (account == null) {
                //未注册
                account = new Account();
                account.setWeChat(appAccount.getUnionId());
                accountDao.saveOrUpdate(account);
            }

            //用户信息
            User user = userDao.getByAccountId(account.getId());
            if (user == null) {
                //未注册
                user = XcxUtils.changeToUser(appAccount);
                user.setAccountId(account.getId());
                userDao.saveOrUpdate(user);
            } else {
                //更新用户信息
                user.setNickname(appAccount.getNickname());
                user.setHeadImgUrl(appAccount.getAvatarUrl());
                user.setSex(Gender.changeToSex(appAccount.getGender()));
                userDao.saveOrUpdate(user);
            }


            Profile profile = profileDao.getByAccountId(account.getId());
            if(null == profile){
                Profile newProfile = new Profile();
                newProfile.setId(account.getId());
                newProfile.setAccountId(account.getId());
                newProfile.setName(appAccount.getNickname());
                newProfile.setHeadImgUrl(appAccount.getAvatarUrl());
                newProfile.setSex(Gender.changeToSex(appAccount.getGender()));
                newProfile.setNickname(appAccount.getNickname());
                profileDao.save(newProfile);
            }

            //缓存用户信息
            userDao.cache(user);

            return user;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 网站应用注册
     */
    @Override
    @Transactional(readOnly = false)
    public User registerByWeb(String token, String code) {
        try {
            Assert.hasText(code, "code值不能为空");
            Assert.hasText(token, "网站应用Token不能为空");

            //查询网站应用
            Application application = applicationDao.getByToken(token);

            if (application == null) {
                throw new IllegalArgumentException("无效的应用Token");
            }

            //appId
            String appId = application.getAppId();
            //secret
            String secret = application.getSecret();

            //查询accessToken
            AccessTokenResponse accessToken = WeChatUtils.getAccessToken(code, appId, secret);

            if (StringUtils.isBlank(accessToken.getOpenId()) && StringUtils.isBlank(accessToken.getUnionId())) {
                throw new ServiceException("获取openId失败");
            }

            WeChatAccount weChatAccount = weChatAccountDao.getByOpenId(accessToken.getOpenId());
            UserInfoResponse userInfo = WeChatUtils.getUserInfo(accessToken.getAccessToken(), appId);
            if (weChatAccount == null) {
                //未注册
                weChatAccount = userInfo.changeToWeChatAccount();
                weChatAccount.setAppId(appId);
                weChatAccountDao.saveOrUpdate(weChatAccount);
            } else {
                //更新微信信息
                weChatAccount.setNickname(userInfo.getNickname());
                weChatAccount.setSex(userInfo.getSex());
                weChatAccount.setCity(userInfo.getCity());
                weChatAccount.setProvince(userInfo.getProvince());
                weChatAccount.setCountry(userInfo.getCountry());
                weChatAccount.setHeadImgUrl(userInfo.getHeadImgUrl());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }
            //更新unionId
            if (!StringUtils.isBlank(accessToken.getUnionId()) && !accessToken.getUnionId().equals(weChatAccount.getUnionId())) {
                weChatAccount.setUnionId(accessToken.getUnionId());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }

            String weChat = StringUtils.isBlank(accessToken.getUnionId()) ? accessToken.getOpenId() : accessToken.getUnionId();
            Account account = accountDao.getByOpenIdOrUnionId(accessToken.getOpenId(), accessToken.getUnionId());
            if (account == null) {
                //未注册
                account = new Account();
                account.setWeChat(weChat);
                accountDao.saveOrUpdate(account);
            }
            //更新unionId
            if (!account.getWeChat().equals(weChat)) {
                account.setWeChat(weChat);
                accountDao.saveOrUpdate(account);
            }

            //用户信息
            User user = userDao.getByAccountId(account.getId());
            if (user == null) {
                //未注册
                user = WeChatUtils.changeToUser(weChatAccount);
                user.setAccountId(account.getId());
                userDao.saveOrUpdate(user);
            } else {
                //更新用户信息
                user.setNickname(weChatAccount.getNickname());
                user.setHeadImgUrl(weChatAccount.getHeadImgUrl());
                user.setSex(WeChatSex.changeToSex(weChatAccount.getSex()));
                userDao.saveOrUpdate(user);
            }

            //缓存用户信息
            userDao.cache(user);

            return user;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 小程序获取用户电话
     */
    @Override
    @Transactional(readOnly = false)
    public String phoneByXcx(String token, String accountId, String sessionKey, String encryptedData, String iv) {
        try {
            Assert.hasText(token, "小程序Token不能为空");
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
            if (decrypt.getWatermark().get("appid").equals(appId)) {
                Account account = accountDao.getById(accountId);
                if (account != null) {
                    account.setPhone(decrypt.getPurePhoneNumber());
                    accountDao.saveOrUpdate(account);

                    //更新小程序用户信息
                    XcxAccount xcxAccount = xcxAccountDao.getByOpenIdOrUnionId(appId, account.getWeChat(), account.getWeChat());
                    if (xcxAccount != null) {
                        xcxAccount.setPhoneNumber(decrypt.getPhoneNumber());
                        xcxAccount.setPurePhoneNumber(decrypt.getPurePhoneNumber());
                        xcxAccount.setCountryCode(decrypt.getCountryCode());
                        xcxAccountDao.saveOrUpdate(xcxAccount);
                    }

                    //更新用户信息
                    User user = userDao.getByAccountId(account.getId());
                    if (user != null) {
                        user.setPhone(decrypt.getPurePhoneNumber());
                        userDao.saveOrUpdate(user);
                    }

                    return decrypt.getPhoneNumber();
                }
            }

            throw new ServiceException("用户信息水印认证失败");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 小程序获取用户地理位置
     */
    @Override
    @Transactional(readOnly = false)
    public void locationByXcx(String token, String accountId, Double latitude, Double longitude) {
        try {
            Assert.hasText(accountId, "账户ID不能为空");
            Assert.hasText(token, "小程序Token不能为空");

            //查询小程序应用
            Application application = applicationDao.getByToken(token);
            //appId
            String appId = application.getAppId();

            Account account = accountDao.getById(accountId);
            XcxAccount xcxAccount = xcxAccountDao.getByOpenIdOrUnionId(appId, account.getWeChat(), account.getWeChat());

            if (xcxAccount != null) {
                if (latitude != null) {
                    xcxAccount.setLatitude(latitude);
                }
                if (longitude != null) {
                    xcxAccount.setLongitude(longitude);
                }

                xcxAccountDao.saveOrUpdate(xcxAccount);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 授权查询账户ID
     */
    @Override
    public String getAccountIdByAuth(String code, String token) {
        try {
            Assert.hasText(code, "授权Code不能为空");
            Assert.hasText(token, "公众号Token不能为空");

            //查询公众号应用
            Application application = applicationDao.getByToken(token);

            if (application == null) {
                throw new IllegalArgumentException("无效的应用Token");
            }

            AccessTokenResponse accessToken = WechatUtils.getAccessTokenByCode(code, application.getAppId(), application.getSecret());

            if (accessToken != null && !StringUtils.isBlank(accessToken.getOpenId()) && !StringUtils.isBlank(accessToken.getUnionId())) {
                Account account = accountDao.getByOpenIdOrUnionId(accessToken.getOpenId(), accessToken.getUnionId());

                return account == null ? null : account.getId();
            }

            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }


    /**
     * 授权查询账户ID
     */
    @Override
    public Account getById(String accountId) {
        try {
            Assert.hasText(accountId, "accountId不能为空");
            return accountDao.getById(accountId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 授权查询账户ID
     */
    @Override
    public Long countByPhone(String phone) {
        try {
            Assert.hasText(phone, "phone不能为空");
            return accountDao.countByPhone(phone);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void saveOrUpdate(Account account) {
        accountDao.saveOrUpdate(account);
    }
}
