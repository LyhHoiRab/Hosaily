package com.lab.hosaily.core.app.dao;

import com.lab.hosaily.core.app.dao.mapper.SellerClientRelationMapper;
import com.lab.hosaily.core.app.entity.SellerClientRelation;
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
public class SellerClientRelationDao {

    private static Logger logger = LoggerFactory.getLogger(SellerClientRelationDao.class);

    @Autowired
    private SellerClientRelationMapper mapper;

    /**
     * 保存或更新
     */
    public void save(SellerClientRelation sellerClientRelation) {
        try {
            Assert.notNull(sellerClientRelation, "信息不能为空");
            sellerClientRelation.setCreateTime(new Date());
            mapper.save(sellerClientRelation);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public SellerClientRelation getById(String sellerId, String clientId) {
        try {
            Assert.hasText(sellerId, "sellerId不能为空");
            Assert.hasText(clientId, "clientId不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("sellerId", sellerId));
            criteria.and(Restrictions.eq("clientId", clientId));

            return mapper.getByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<SellerClientRelation> page(PageRequest pageRequest, String sellerId) {
        try {
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("createTime"));
//
            if (!StringUtils.isBlank(sellerId)) {
                criteria.and(Restrictions.like("sellerId", sellerId));
            }
            Long count = mapper.countByParams(criteria);
            List<SellerClientRelation> list = mapper.findByParams(criteria);

            return new Page<SellerClientRelation>(list, pageRequest, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<SellerClientRelation> list(String sellerId) {
        try {
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("createTime"));

            if (!StringUtils.isBlank(sellerId)) {
                criteria.and(Restrictions.eq("sellerId", sellerId));
            }

            return mapper.findByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
