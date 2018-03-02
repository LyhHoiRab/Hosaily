package com.lab.hosaily.core.product.dao;

import com.lab.hosaily.core.product.dao.mapper.ServiceMapper;
import com.lab.hosaily.core.product.entity.Service;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class ServiceDao{

    private static Logger logger = LoggerFactory.getLogger(ServiceDao.class);

    @Autowired
    private ServiceMapper mapper;

    /**
     * 保存
     */
    public void save(String masterId, List<Service> services){
        try{
            Assert.hasText(masterId, "Master ID不能为空");

            if(services != null && !services.isEmpty()){
                Date now = new Date();

                for(Service service : services){
                    Assert.notNull(service, "服务信息不能为空");
                    Assert.notNull(service.getType(), "服务类型不能为空");

                    service.setId(UUIDGenerator.by32());
                    service.setMasterId(masterId);
                    service.setCreateTime(now);
                }

                mapper.save(services);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    public void deleteByMasterId(String masterId){
        try{
            Assert.hasText(masterId, "Master ID不能为空");

            mapper.deleteByMasterId(masterId);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除
     */
    public void delete(String id){
        try{
            Assert.hasText(id, "服务ID不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
