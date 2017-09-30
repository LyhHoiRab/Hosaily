package com.lab.hosaily.core.application.service;

import com.lab.hosaily.commons.consts.AuthorizationConsts;
import com.lab.hosaily.core.application.dao.ApplicationDao;
import com.lab.hosaily.core.application.entity.Application;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.AESUtils;
import com.rab.babylon.commons.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService{

    private static Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private ApplicationDao applicationDao;

    /**
     * 查询生成二维码参数
     */
    @Override
    public Map<String, Object> getQRParams(String token, String redirectUrl){
        try{
            Assert.hasText(token, "应用Token不能为空");

            if(StringUtils.isBlank(redirectUrl)){
                redirectUrl = AuthorizationConsts.DEFAULT_REDIRECT;
            }

            Application application = applicationDao.getByToken(token);

            if(application == null){
                throw new ServiceException(String.format("无效的应用Token[%s]", token));
            }

            String state = AESUtils.encryptBy128(AuthorizationConsts.ENCRYP_PREFIX + redirectUrl, AuthorizationConsts.DECRYPT_KEY);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AuthorizationConsts.NODE_APPID, application.getAppId());
            params.put(AuthorizationConsts.NODE_REDIRECT_URL, AuthorizationConsts.getAuthorizeApi());
            params.put(AuthorizationConsts.NODE_STATE, state);

            return params;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
