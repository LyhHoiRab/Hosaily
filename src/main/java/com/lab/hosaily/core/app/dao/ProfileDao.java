package com.lab.hosaily.core.app.dao;

import com.lab.hosaily.core.app.dao.mapper.ProfileMapper;
import com.lab.hosaily.core.app.entity.Profile;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class ProfileDao {

    private static Logger logger = LoggerFactory.getLogger(ProfileDao.class);

    @Autowired
    private ProfileMapper mapper;

//    /**
//     * 保存或更新
//     */
//    public void saveOrUpdate(Profile profile){
//        try{
//            Assert.notNull(profile, "导师信息不能为空");
//
//            if(StringUtils.isBlank(profile.getId())){
//
//                Assert.hasText(profile.getOrganizationId(), "企业ID不能为空");
//
//                profile.setId(UUIDGenerator.by32());
//                profile.setCreateTime(new Date());
//                mapper.save(profile);
//            }else{
//                profile.setUpdateTime(new Date());
//                mapper.update(profile);
//            }
//        }catch(Exception e){
//            logger.error(e.getMessage(), e);
//            throw new DataAccessException(e.getMessage(), e);
//        }
//    }


    /**
     * 保存或更新
     */
    public void save(Profile profile) {
        try {
            Assert.notNull(profile, "导师信息不能为空");
            Assert.notNull(profile.getId(), "ID不能为空");
            profile.setCreateTime(new Date());
            mapper.save(profile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    /**
     * 保存或更新
     */
    public void update(Profile profile) {
        try {
            Assert.notNull(profile, "导师信息不能为空");
            Assert.notNull(profile.getId(), "ID不能为空");
            profile.setUpdateTime(new Date());
            mapper.update(profile);

        } catch (
                Exception e
                )

        {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }

    }

    /**
     * 根据ID查询
     */
    public Profile getById(String id) {
        try {
            Assert.hasText(id, "导师ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("p.id", id));

            return mapper.getByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Profile getByAccountId(String accountId) {
        try {
            Assert.hasText(accountId, "accountId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("p.accountId", accountId));

            return mapper.getByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Profile> page(PageRequest pageRequest, Integer signAgreement, Integer signProfile, Integer uploaded, String name, String sellerId) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            if (!StringUtils.isBlank(name)) {
                name = "'%" + name + "%'";
            } else {
                name = null;
            }
            Long count = mapper.countByMixs(signAgreement, signProfile, uploaded, name, sellerId);
            List<Profile> list = mapper.findByMixs(signAgreement, signProfile, uploaded, name, sellerId, pageRequest.getOffset(), pageRequest.getPageSize());

            return new Page<Profile>(list, pageRequest, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Profile> findClientsPage(PageRequest pageRequest, String clientName, String advisorId) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            Long count = mapper.countByClientsPage(clientName, "'" + advisorId + "'");
            List<Profile> list = mapper.findByClientsPage(clientName, "'" + advisorId + "'", pageRequest.getOffset(), pageRequest.getPageSize());

            return new Page<Profile>(list, pageRequest, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }




    /**
     * 查询列表
     */
    public List<Profile> list(String advisorName, Integer role) {
        try {
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("p.createTime"));

            if (!StringUtils.isBlank(advisorName)) {
                criteria.and(Restrictions.like("p.name", advisorName));
            }

            if (null != role) {
                criteria.and(Restrictions.eq("p.role", role));
            }
//            if(!StringUtils.isBlank(name)){
//                criteria.and(Restrictions.like("a.name", name));
//            }
//            if(state != null){
//                criteria.and(Restrictions.eq("a.state", state.getId()));
//            }
//            if(!StringUtils.isBlank(organizationId)){
//                criteria.and(Restrictions.eq("a.organizationId", organizationId));
//            }
//            if(!StringUtils.isBlank(organizationToken)){
//                criteria.and(Restrictions.eq("o.token", organizationToken));
//            }

            return mapper.findByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
