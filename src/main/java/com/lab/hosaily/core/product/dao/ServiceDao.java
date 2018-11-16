package com.lab.hosaily.core.product.dao;

import com.lab.hosaily.core.product.consts.ServiceType;
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
     * 保存
     */
    public void newSave(String masterId, List<Service> services, Double callUnitPrice, Integer callTimes){
        try{
            Assert.hasText(masterId, "Master ID不能为空");

            if(services != null && !services.isEmpty()){
                Date now = new Date();

                for(Service service : services){
                    Assert.notNull(service, "服务信息不能为空");
                    Assert.notNull(service.getType(), "服务类型不能为空");

                    System.out.println("AAAAAAAAAAAAAAAAA: " + service.getName());
                    if(null != callUnitPrice && null != callTimes){
                        if(ServiceType.ONE_TO_ONE.equals(service.getType())){
                            if(service.getName().startsWith("电话咨询指导（")){
                                System.out.println("BBBBBBBBBB: " + service.getName());
//                                电话咨询指导（60分钟/次），单价为1200 元/次
                                service.setName("电话咨询指导（60分钟/次），单价为¥" + callUnitPrice.intValue() + "元/次");
                                service.setDescription("电话咨询指导（60分钟/次），单价为¥" + callUnitPrice.intValue() + "元/次");
//                                aaaa: 电话咨询指导（60分钟/次），单价为1600 元/次
                                service.setUnitPrice(callUnitPrice);
                                service.setTime(callTimes);
                            }
                        }
                    }

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


//    public static void main(String[] args){
//        Double asd = new Double("1111");
//        System.out.println(asd);
//        System.out.println(asd.intValue());
//    }
}
