package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.dao.mapper.ContractMapper;
import com.lab.hosaily.core.client.entity.Contract;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;

@Repository
public class ContractDao{

    private Logger logger = LoggerFactory.getLogger(ContractDao.class);

    @Autowired
    private ContractMapper mapper;

    public void save(Contract contract){
        try{
            Assert.notNull(contract, "合同信息不能为空");

            contract.setId(UUIDGenerator.by32());
            contract.setCreateTime(new Date());

            mapper.save(contract);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Contract getByVersion(Integer version){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("r.levelUp"));
            criteria.sort(Restrictions.asc("r.sort"));
            criteria.and(Restrictions.eq("c.version", version));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
