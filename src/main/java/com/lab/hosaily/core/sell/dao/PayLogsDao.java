package com.lab.hosaily.core.sell.dao;

import com.lab.hosaily.core.sell.dao.mapper.PayLogsMapper;
import com.lab.hosaily.core.sell.entity.PayLogs;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class PayLogsDao{

    private static Logger logger = LoggerFactory.getLogger(PayLogsDao.class);

    @Autowired
    private PayLogsMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(PayLogs logs){
        try{
            Assert.notNull(logs, "支付记录信息不能为空");

            if(StringUtils.isBlank(logs.getId())){
                Assert.notNull(logs.getOrder(), "订单记录不能为空");
                Assert.hasText(logs.getOrder().getId(), "订单记录ID不能为空");
                Assert.notNull(logs.getPayment(), "支付方式不能为空");
                Assert.hasText(logs.getPayment().getId(), "支付方式ID不能为空");
                Assert.notNull(logs.getPay(), "支付金额不能为空");

                logs.setId(UUIDGenerator.by32());
                logs.setCreateTime(new Date());
                mapper.save(logs);
            }else{
                logs.setUpdateTime(new Date());
                mapper.update(logs);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 批量保存
     */
    public void saveBatch(List<PayLogs> logs){
        try{
            Assert.notEmpty(logs, "支付记录不能为空");

            for(PayLogs log : logs){
                Assert.notNull(log.getOrder(), "订单记录不能为空");
                Assert.hasText(log.getOrder().getId(), "订单记录ID不能为空");
                Assert.notNull(log.getPayment(), "支付方式不能为空");
                Assert.hasText(log.getPayment().getId(), "支付方式ID不能为空");
                Assert.notNull(log.getPay(), "支付金额不能为空");

                log.setId(UUIDGenerator.by32());
                log.setCreateTime(new Date());
            }

            mapper.saveBatch(logs);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
