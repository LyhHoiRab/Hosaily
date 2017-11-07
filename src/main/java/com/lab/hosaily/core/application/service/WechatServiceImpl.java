package com.lab.hosaily.core.application.service;

import com.lab.hosaily.core.application.dao.ApplicationDao;
import com.lab.hosaily.core.application.entity.Application;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.utils.SHAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
public class WechatServiceImpl implements WechatService{

    private static Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    @Autowired
    private ApplicationDao applicationDao;

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
}
