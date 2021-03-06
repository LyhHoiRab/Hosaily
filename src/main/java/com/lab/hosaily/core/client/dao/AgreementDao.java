package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.consts.AgreementState;
import com.lab.hosaily.core.client.dao.mapper.AgreementMapper;
import com.lab.hosaily.core.client.entity.Agreement;
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
import org.wah.doraemon.utils.DateUtils;

import java.util.Date;
import java.util.List;

@Repository
public class AgreementDao{

    private static Logger logger = LoggerFactory.getLogger(AgreementDao.class);

    @Autowired
    private AgreementMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Agreement agreement){
        try{
            Assert.notNull(agreement, "协议信息不能为空");

            if(StringUtils.isBlank(agreement.getId())){
                Assert.hasText(agreement.getServiceId(), "销售ID不能为空");

                agreement.setId(UUIDGenerator.by32());
                agreement.setCreateTime(new Date());
                mapper.save(agreement);
            }else{
                agreement.setUpdateTime(new Date());
                mapper.update(agreement);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 状态回退
     */
    public void backToEdit(Agreement agreement){
        try{
            Assert.notNull(agreement, "合同信息不能为空");
            Assert.hasText(agreement.getId(), "合同ID不能为空");

            mapper.backToEdit(agreement);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public Agreement getById(String id){
        try{
            Assert.hasText(id, "协议ID不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("s.sort"));
            criteria.and(Restrictions.eq("a.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据购买记录ID查询
     */
    public Agreement getByPurchaseId(String purchaseId){
        try{
            Assert.hasText(purchaseId, "购买记录ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("a.purchaseId", purchaseId));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public List<Agreement> find(String accountId, String serviceId, AgreementState state){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.desc("createTime"));

            if(StringUtils.isNotBlank(accountId)){
                criteria.and(Restrictions.eq("accountId", accountId));
            }
            if(StringUtils.isNotBlank(serviceId)){
                criteria.and(Restrictions.eq("serviceId", serviceId));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Page<Agreement> page(PageRequest pageRequest, String accountId, String serviceId, AgreementState state){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.desc("createTime"));
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(StringUtils.isNotBlank(accountId)){
                criteria.and(Restrictions.eq("accountId", accountId));
            }
            if(StringUtils.isNotBlank(serviceId)){
                criteria.and(Restrictions.eq("serviceId", serviceId));
            }
            if(state != null){
                criteria.and(Restrictions.eq("state", state.getId()));
            }

            List<Agreement> list = mapper.findByParams(criteria);
            Long total = mapper.countByParams(criteria);

            return new Page<Agreement>(list, pageRequest, total);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public List<Agreement> findInvalid(){
        try{
            Date now = DateUtils.lastTimeOfDate(new Date());

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("state", AgreementState.TAKE_EFFECT));
            criteria.and(Restrictions.lt("deadline", now));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void invalid(List<String> ids){
        try{
            Assert.notEmpty(ids, "失效的合同ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.in("id", ids));

            mapper.updateState(AgreementState.NOT_EFFECT, criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
