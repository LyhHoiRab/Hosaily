package com.lab.hosaily.core.sell.service;

import com.lab.hosaily.core.account.dao.UserDao;
import com.lab.hosaily.core.course.dao.AdvisorDao;
import com.lab.hosaily.core.course.entity.Advisor;
import com.lab.hosaily.core.organization.dao.OrganizationDao;
import com.lab.hosaily.core.organization.entity.Organization;
import com.lab.hosaily.core.popularize.dao.WechatDao;
import com.lab.hosaily.core.popularize.entity.Wechat;
import com.lab.hosaily.core.sell.webservice.SalesRestController;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.core.account.entity.User;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SalesServiceImpl implements SalesService{

    private static Logger logger = LoggerFactory.getLogger(SalesRestController.class);

    @Autowired
    private AdvisorDao advisorDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private WechatDao wechatDao;

    /**
     * 微信号验证导师
     */
    @Override
    public Advisor verifyAdvisor(String wechat, String organizationToken){
        try{
            Assert.hasText(wechat, "导师微信不能为空");

//            List<User> users = userDao.list(null, null, wechat, null, null, null, null, organizationToken);
//
//            if(users != null && !users.isEmpty()){
//                Advisor advisor = advisorDao.getById(users.get(0).getAdvisorId());
//                Organization organization = organizationDao.getById(advisor.getOrganizationId());
//
//                return organization.getToken().equalsIgnoreCase(organizationToken) ? advisor : null;
//            }
//
//            return null;

            Organization organization = organizationDao.getByToken(organizationToken);

            if(organization != null){
                Wechat wechatInfo = wechatDao.getByOrganizationIdAndWxno(organization.getId(), wechat);

                if(wechatInfo != null){
                    return wechatInfo.getAdvisor();
                }
            }

            return null;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
