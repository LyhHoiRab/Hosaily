package com.lab.hosaily.core.application.service;

import com.lab.hosaily.commons.consts.AuthorizationConsts;
import com.lab.hosaily.core.application.dao.ApplicationDao;
import com.lab.hosaily.core.application.entity.Application;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.AESUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ApplicationServiceImpl implements ApplicationService{

    private static Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private ApplicationDao applicationDao;

    /**
     * 保存应用
     */
    @Override
    @Transactional(readOnly = false)
    public void save(Application application){
        try{
            Assert.notNull(application, "应用信息不能为空");
            Assert.hasText(application.getAppId(), "应用AppId不能为空");
            Assert.hasText(application.getToken(), "应用Token不能为空");
            Assert.notNull(application.getType(), "应用类型不能为空");

            applicationDao.saveOrUpdate(application);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 更新应用
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Application application){
        try{
            Assert.notNull(application, "应用信息不能为空");
            Assert.hasText(application.getId(), "应用信息ID不能为空");

            applicationDao.saveOrUpdate(application);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 查询生成二维码参数
     */
    @Override
    public Map<String, Object> getQRParams(String sessionId, String token, String redirectUrl, String basePath){
        try{
            Assert.hasText(token, "应用Token不能为空");

            if(StringUtils.isBlank(redirectUrl)){
                redirectUrl = AuthorizationConsts.DEFAULT_REDIRECT;
            }

            Application application = applicationDao.getByToken(token);

            if(application == null){
                throw new ServiceException(String.format("无效的应用Token[%s]", token));
            }

            String state = AESUtils.encryptBy128(redirectUrl + "_" + token, sessionId);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put(AuthorizationConsts.NODE_APPID, application.getAppId());
            params.put(AuthorizationConsts.NODE_REDIRECT_URL, basePath + AuthorizationConsts.AUTHORIZE_API);
            params.put(AuthorizationConsts.NODE_STATE, state);

            return params;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public Page<Application> page(PageRequest pageRequest){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return applicationDao.page(pageRequest);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    @Override
    public Application getById(String id){
        try{
            Assert.hasText(id, "应用ID不能为空");

            return applicationDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
