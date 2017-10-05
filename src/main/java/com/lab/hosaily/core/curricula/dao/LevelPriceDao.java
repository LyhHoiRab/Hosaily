package com.lab.hosaily.core.curricula.dao;

import com.lab.hosaily.core.curricula.dao.mapper.LevelPriceMapper;
import com.lab.hosaily.core.curricula.entity.LevelPrice;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.UsingState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class LevelPriceDao{

    private static Logger logger = LoggerFactory.getLogger(LevelPriceDao.class);

    @Autowired
    private LevelPriceMapper mapper;

    /**
     * 批量保存
     */
    public void saveByBatch(List<LevelPrice> list){
        try{
            Assert.notEmpty(list, "价格列表不能为空");

            Date now = new Date();

            for(LevelPrice price : list){
                Assert.notNull(price.getPrice(), "价格不能为空");
                Assert.notNull(price.getEffective(), "有效天数不能为空");
                Assert.hasText(price.getLevelId(), "等级ID不能为空");

                price.setId(UUIDGenerator.by32());
                price.setState(UsingState.NORMAL);
                price.setCreateTime(now);
            }

            mapper.saveByBatch(list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 批量更新
     */
    public void updateByBatch(List<LevelPrice> list){
        try{
            Assert.notEmpty(list, "价格列表不能为空");

            Date now = new Date();

            for(LevelPrice price : list){
                Assert.hasText(price.getId(), "价格ID不能为空");
                Assert.notNull(price.getPrice(), "价格不能为空");
                Assert.notNull(price.getEffective(), "有效天数不能为空");
                Assert.hasText(price.getLevelId(), "等级ID不能为空");

                price.setUpdateTime(now);
            }

            mapper.updateByBatch(list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除记录
     */
    public void delete(String id){
        try{
            Assert.hasText(id, "价格ID不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
