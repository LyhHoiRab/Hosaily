package com.lab.hosaily.core.app.dao;



import com.lab.hosaily.core.app.dao.mapper.LoginUserMapper;
import com.lab.hosaily.core.app.entity.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.doraemon.security.exception.DataAccessException;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import org.wah.doraemon.utils.IDGenerator;
import org.wah.doraemon.utils.mybatis.Criteria;
import org.wah.doraemon.utils.mybatis.Restrictions;

import java.util.Date;
import java.util.List;

@Repository
public class LoginUserDao {

    private Logger logger = LoggerFactory.getLogger(LoginUserDao.class);

    @Autowired
    private LoginUserMapper mapper;

    public void save(LoginUser loginUser) {
        try {
            Assert.notNull(loginUser, "用户信息不能为空");
            loginUser.setId(IDGenerator.uuid32());
            loginUser.setCreateTime(new Date());
            mapper.save(loginUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void update(LoginUser loginUser) {
        try {
            loginUser.setUpdateTime(new Date());
            mapper.update(loginUser);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public LoginUser getByPhone(String phone) {
        try {
            Assert.hasText(phone, "phone不能为空");
            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("phone", phone));
            return mapper.get(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Page<LoginUser> page(PageRequest pageRequest, String name,
                               String situation, String startTime, String endTime, String process, String follower) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.desc("createTime"));

            criteria.and(Restrictions.isNotNull("id"));

//            if (StringUtils.isNotBlank(name)) {
//                criteria.and(Restrictions.like("name", name));
//            }
            if (StringUtils.isNotBlank(situation)) {
                criteria.and(Restrictions.eq("situation", situation));
            }
            if (StringUtils.isNotBlank(startTime)) {
                criteria.and(Restrictions.gt("create_time", startTime));
            }
            if(StringUtils.isNotBlank(endTime)){
                criteria.and(Restrictions.lt("create_time", endTime));
            }
            if(StringUtils.isNotBlank(process)){
                criteria.and(Restrictions.eq("process", process));
            }
            if(StringUtils.isNotBlank(follower)){
                criteria.and(Restrictions.eq("follower", follower));
            }

            List<LoginUser> list = mapper.find(criteria, name);
            Long total = mapper.count(criteria);
            return new Page<LoginUser>(list, total, pageRequest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public List<LoginUser> findAllByMix(String name, String situation, String startTime, String endTime) {
        try {
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.desc("createTime"));
            if (StringUtils.isNotBlank(name)) {
                criteria.and(Restrictions.like("name", name));
            }
            if (StringUtils.isNotBlank(situation)) {
                criteria.and(Restrictions.eq("situation", situation));
            }
            if (StringUtils.isNotBlank(startTime)) {
                criteria.and(Restrictions.gt("create_time", startTime));
            }
            if(StringUtils.isNotBlank(endTime)){
                criteria.and(Restrictions.lt("create_time", endTime));
            }
            List<LoginUser> list = mapper.find(criteria, null);
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
