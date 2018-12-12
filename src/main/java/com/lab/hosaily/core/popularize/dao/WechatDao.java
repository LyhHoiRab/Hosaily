package com.lab.hosaily.core.popularize.dao;

import com.lab.hosaily.core.popularize.dao.mapper.WechatMapper;
import com.lab.hosaily.core.popularize.entity.Wechat;
import com.rab.babylon.commons.security.exception.DataAccessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.wah.doraemon.security.request.Page;
import org.wah.doraemon.security.request.PageRequest;
import org.wah.doraemon.utils.IDGenerator;
import org.wah.doraemon.utils.mybatis.Criteria;
import org.wah.doraemon.utils.mybatis.Restrictions;

import java.util.Date;
import java.util.List;

@Repository
public class WechatDao{

    private Logger logger = LoggerFactory.getLogger(WechatDao.class);

    @Autowired
    private WechatMapper mapper;

    public void saveOrUpdate(Wechat wechat){
        try{
            Assert.notNull(wechat, "微信信息不能为空");

            if(StringUtils.isBlank(wechat.getId())){
                Assert.hasText(wechat.getWxno(), "微信号不能为空");
                Assert.hasText(wechat.getOrganizationId(), "微信所属企业ID不能为空");
                Assert.notNull(wechat.getState(), "微信使用状态不能为空");

                wechat.setId(IDGenerator.uuid32());
                wechat.setCreateTime(new Date());
                mapper.save(wechat);
            }else{
                wechat.setUpdateTime(new Date());
                mapper.update(wechat);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Wechat getById(String id){
        try{
            Assert.hasText(id, "微信ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.id", id));

            return mapper.get(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Wechat getByOrganizationIdAndWxno(String organizationId, String wxno){
        try{
            Assert.hasText(organizationId, "微信所属企业ID不能为空");
            Assert.hasText(wxno, "微信号不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.organizationId", organizationId));
            criteria.and(Restrictions.eq("w.wxno", wxno));

            return mapper.get(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public boolean existByOrganizationIdAndWxno(String organizationId, String wxno){
        try{
            Assert.hasText(organizationId, "微信所属企业ID不能为空");
            Assert.hasText(wxno, "微信号不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("w.organizationId", organizationId));
            criteria.and(Restrictions.eq("w.wxno", wxno));

            Long total = mapper.count(criteria);

            return (total != null && total > 0);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Page<Wechat> page(PageRequest pageRequest, String organizationId, String wxno, String nickname, String remark,
                             String seller, String advisorId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(StringUtils.isNotBlank(organizationId)){
                criteria.and(Restrictions.eq("w.organizationId", organizationId));
            }
            if(StringUtils.isNotBlank(wxno)){
                criteria.and(Restrictions.eq("w.wxno", wxno));
            }
            if(StringUtils.isNotBlank(nickname)){
                criteria.and(Restrictions.like("w.nickname", nickname));
            }
            if(StringUtils.isNotBlank(remark)){
                criteria.and(Restrictions.eq("w.remark", remark));
            }
            if(StringUtils.isNotBlank(seller)){
                criteria.and(Restrictions.eq("w.seller", seller));
            }
            if(StringUtils.isNotBlank(advisorId)){
                criteria.and(Restrictions.eq("w.advisorId", advisorId));
            }

            List<Wechat> list  = mapper.find(criteria);
            Long         total = mapper.count(criteria);

            return new Page<Wechat>(list, total, pageRequest);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
