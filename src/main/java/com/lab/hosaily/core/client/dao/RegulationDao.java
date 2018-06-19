package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.dao.mapper.RegulationMapper;
import com.lab.hosaily.core.client.entity.Regulation;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class RegulationDao{

    private Logger logger = LoggerFactory.getLogger(RegulationDao.class);

    @Autowired
    private RegulationMapper mapper;

    public void saveOrUpdate(Regulation regulation){
        try{
            Assert.notNull(regulation, "条例信息不能为空");

            if(StringUtils.isBlank(regulation.getId())){
                Assert.hasText(regulation.getContractId(), "合同ID不能为空");

                regulation.setId(UUIDGenerator.by32());
                regulation.setCreateTime(new Date());
                mapper.save(regulation);
            }else{
                regulation.setUpdateTime(new Date());
                mapper.update(regulation);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public List<Regulation> findByParentId(String parentId){
        try{
            Assert.hasText(parentId, "父级ID不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("sort"));
            criteria.and(Restrictions.eq("parentId", parentId));

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
