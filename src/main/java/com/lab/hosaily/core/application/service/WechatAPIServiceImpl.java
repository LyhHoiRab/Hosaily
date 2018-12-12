package com.lab.hosaily.core.application.service;

import com.lab.hosaily.commons.utils.WeChatUtils;
import com.lab.hosaily.core.account.consts.WeChatSex;
import com.lab.hosaily.core.account.dao.AccountDao;
import com.lab.hosaily.core.account.dao.UserDao;
import com.lab.hosaily.core.account.dao.WeChatAccountDao;
import com.lab.hosaily.core.account.entity.WeChatAccount;
import com.lab.hosaily.core.application.dao.ApplicationDao;
import com.lab.hosaily.core.application.entity.Application;
import com.lab.hosaily.core.application.utils.WechatUtils;
import com.lab.hosaily.core.application.utils.XMLUtils;
import com.lab.hosaily.core.application.utils.consts.WechatXMLConsts;
import com.lab.hosaily.core.application.utils.response.WechatUserInfoResponse;
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

import java.util.Arrays;
import java.util.Map;

@Service
public class WechatAPIServiceImpl implements WechatAPIService {

    private static Logger logger = LoggerFactory.getLogger(WechatAPIServiceImpl.class);

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private WeChatAccountDao weChatAccountDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShardedJedisPool pool;

    /**
     * 微信平台接入验证
     */
    @Override
    public boolean authorize(String token, String signature, String timestamp, String nonce){
        try{
            Assert.hasText(token, "应用Token不能为空");
            Assert.hasText(signature, "认证签名不能为空");

            Application application = applicationDao.getByToken(token);

            if(application == null){
                throw new IllegalArgumentException("无效的应用Token");
            }

            //字典排序
            String[] array = new String[]{token, timestamp, nonce};
            Arrays.sort(array);
            //拼接
            StringBuffer sb = new StringBuffer();
            for(String param : array){
                sb.append(param);
            }
            //SHA1加密
            String encryption = SHAUtils.bySHA1(sb.toString());

            //校验
            return signature.equalsIgnoreCase(encryption);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 微信报文处理入口
     */
    @Override
    public String reply(String token, Map<String, Object> xml){
        try{
            Assert.hasText(token, "公众号Token不能为空");
            Assert.notEmpty(xml, "微信报文不能为空");

            Application application = applicationDao.getByToken(token);

            if(application == null){
                throw new IllegalArgumentException("无效的应用Token");
            }

            String key = application.getAesKey();
            xml = XMLUtils.decrypt((String) xml.get(WechatXMLConsts.NODE_ENCRYPT), key);

            //消息类型
            String msgType = (String) xml.get(WechatXMLConsts.NODE_MSG_TYPE);

            if(WechatXMLConsts.MSG_TYPE_EVENT.equalsIgnoreCase(msgType)){
                //事件消息
                return event(xml, application);
            }else if(WechatXMLConsts.MSG_TYPE_TEXT.equalsIgnoreCase(msgType)){
                //文本消息
                return "SUCCESS";
            }else if(WechatXMLConsts.MSG_TYPE_IMG.equalsIgnoreCase(msgType)){
                //图片消息
                return "SUCCESS";
            }else if(WechatXMLConsts.MSG_TYPE_LINK.equalsIgnoreCase(msgType)){
                //链接消息
                return "SUCCESS";
            }else if(WechatXMLConsts.MSG_TYPE_LOCATION.equalsIgnoreCase(msgType)){
                //地理位置消息
                return "SUCCESS";
            }else if(WechatXMLConsts.MSG_TYPE_SHORTVIDEO.equalsIgnoreCase(msgType)){
                //小视频消息
                return "SUCCESS";
            }else if(WechatXMLConsts.MSG_TYPE_VIDEO.equalsIgnoreCase(msgType)){
                //视频消息
                return "SUCCESS";
            }else if(WechatXMLConsts.MSG_TYPE_VOICE.equalsIgnoreCase(msgType)){
                //语音消息
                return "SUCCESS";
            }else if(WechatXMLConsts.MSG_TYPE_LOCATION.equalsIgnoreCase(msgType)){
                //地理位置消息
                return "SUCCESS";
            }

            return "SUCCESS";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 微信事件处理
     */
    @Override
    public String event(Map<String, Object> xml, Application application){
        try{
            Assert.notEmpty(xml, "xml报文不能为空");
            Assert.notNull(application, "公众号应用不能为空");

            //事件类型
            String eventType = (String) xml.get(WechatXMLConsts.NODE_EVENT);

            if(WechatXMLConsts.EVENT_TYPE_SUBSCRIBE.equalsIgnoreCase(eventType)){
                //关注事件
                return subscribe(xml, application);
            }else if(WechatXMLConsts.EVENT_TYP_LOCATION.equalsIgnoreCase(eventType)){
                //上报地理位置事件
                return location(xml, application);
            }else if(WechatXMLConsts.EVENT_TYPE_CLICK.equalsIgnoreCase(eventType)){
                //菜单点击事件
                return "SUCCESS";
            }else if(WechatXMLConsts.EVENT_TYPE_SCAN.equalsIgnoreCase(eventType)){
                //扫描事件
                return "SUCCESS";
            }else if(WechatXMLConsts.EVENT_TYPE_UNSUBSCRIBE.equalsIgnoreCase(eventType)){
                //取关事件
                return "SUCCESS";
            }else if(WechatXMLConsts.EVENT_TYPE_VIEW.equalsIgnoreCase(eventType)){
                //菜单跳转链接事件
                return "SUCCESS";
            }

            return "SUCCESS";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 微信关注事件处理
     */
    @Transactional(readOnly = false)
    @Override
    public String subscribe(Map<String, Object> xml, Application application){
        try{
            Assert.notEmpty(xml, "xml报文不能为空");
            Assert.notNull(application, "公众号应用不能为空");

            //用户openId
            String openId = (String) xml.get(WechatXMLConsts.NODE_FROM_USER_NAME);
            //微信用户信息
            WechatUserInfoResponse userInfo  = WechatUtils.getUserInfo(pool, application.getAppId(), application.getSecret(), openId);

            if(userInfo == null){
                throw new ServiceException("获取用户信息失败");
            }

            WeChatAccount weChatAccount = weChatAccountDao.getByOpenId(userInfo.getOpenId());
            if(weChatAccount == null){
                //未注册
                weChatAccount = WechatUtils.changeToWeChatAccount(userInfo);
                weChatAccount.setAppId(application.getAppId());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }else{
                //更新微信信息
                weChatAccount.setNickname(userInfo.getNickname());
                weChatAccount.setSex(userInfo.getSex());
                weChatAccount.setLanguage(userInfo.getLanguage());
                weChatAccount.setCity(userInfo.getCity());
                weChatAccount.setProvince(userInfo.getProvince());
                weChatAccount.setCountry(userInfo.getCountry());
                weChatAccount.setHeadImgUrl(userInfo.getHeadimgurl());
                weChatAccount.setSubscribeTime(userInfo.getSubscribeTime());
                weChatAccount.setRemark(userInfo.getRemark());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }
            //更新unionId
            if(!StringUtils.isBlank(userInfo.getUnionId()) && !userInfo.getUnionId().equals(weChatAccount.getUnionId())){
                weChatAccount.setUnionId(userInfo.getUnionId());
                weChatAccountDao.saveOrUpdate(weChatAccount);
            }

            String weChat = StringUtils.isBlank(userInfo.getUnionId()) ? userInfo.getOpenId() : userInfo.getUnionId();
            Account account = accountDao.getByOpenIdOrUnionId(userInfo.getOpenId(), userInfo.getUnionId());
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
            }else{
                //更新用户信息
                user.setNickname(weChatAccount.getNickname());
                user.setHeadImgUrl(weChatAccount.getHeadImgUrl());
                user.setSex(WeChatSex.changeToSex(weChatAccount.getSex()));
                userDao.saveOrUpdate(user);
            }

            return "SUCCESS";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 微信地理位置事件处理
     */
    @Override
    @Transactional(readOnly = false)
    public String location(Map<String, Object> xml, Application application){
        try{
            Assert.notEmpty(xml, "xml报文不能为空");
            Assert.notNull(application, "公众号应用不能为空");

            //用户openId
            String openId = (String) xml.get(WechatXMLConsts.NODE_FROM_USER_NAME);
            //用户信息
            WeChatAccount account = weChatAccountDao.getByOpenId(openId);

            if(account != null){
                //纬度
                String latitude = (String) xml.get(WechatXMLConsts.LOCATION_LATITUDE);
                //经度
                String longitude = (String) xml.get(WechatXMLConsts.LOCATION_LONGITUDE);
                //精度
                String precision = (String) xml.get(WechatXMLConsts.LOCATION_PRECISION);

                if(!StringUtils.isBlank(latitude)){
                    account.setLatitude(Double.parseDouble(latitude));
                }
                if(!StringUtils.isBlank(longitude)){
                    account.setLongitude(Double.parseDouble(longitude));
                }
                if(!StringUtils.isBlank(precision)){
                    account.setPrecision(Double.parseDouble(precision));
                }

                weChatAccountDao.saveOrUpdate(account);
            }

            return "SUCCESS";
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
