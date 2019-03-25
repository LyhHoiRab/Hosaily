package com.lab.hosaily.core.app.dao;



import com.lab.hosaily.core.app.dao.mapper.AppVersionMapper;
import com.lab.hosaily.core.app.entity.AppVersion;
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
public class AppVersionDao {

    private Logger logger = LoggerFactory.getLogger(AppVersionDao.class);

    @Autowired
    private AppVersionMapper mapper;

    public void save(AppVersion appVersion) {
        try {
            Assert.notNull(appVersion, "用户信息不能为空");
            appVersion.setId(IDGenerator.uuid32());
            appVersion.setCreateTime(new Date());
            mapper.save(appVersion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void update(AppVersion appVersion) {
        try {
            appVersion.setUpdateTime(new Date());
            mapper.update(appVersion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public AppVersion getByPhone(String phone) {
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




    public AppVersion getNewestVersion() {
        try {
            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(0l, 1l));
            criteria.sort(Restrictions.desc("createTime"));
            return mapper.get(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    public Page<AppVersion> page(PageRequest pageRequest, String name,
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

            List<AppVersion> list = mapper.find(criteria, name);
            Long total = mapper.count(criteria);
            return new Page<AppVersion>(list, total, pageRequest);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public List<AppVersion> findAllByMix(String name, String situation, String startTime, String endTime) {
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
            List<AppVersion> list = mapper.find(criteria, null);
            return list;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
