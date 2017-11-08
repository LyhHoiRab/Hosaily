package com.lab.hosaily.core.popularize.dao;

import com.lab.hosaily.core.popularize.dao.mapper.AnswerMapper;
import com.lab.hosaily.core.popularize.entity.Answer;
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
public class AnswerDao{

    private static Logger logger = LoggerFactory.getLogger(AnswerDao.class);

    @Autowired
    private AnswerMapper mapper;

    /**
     * 保存或者更新
     */
    public void saveOrUpdate(Answer answer){
        try{
            Assert.notNull(answer, "题库答案信息不能为空");

            if(StringUtils.isBlank(answer.getId())){
                Assert.hasText(answer.getLibraryId(), "题库ID不能为空");

                answer.setId(UUIDGenerator.by32());
                answer.setCreateTime(new Date());
                mapper.save(answer);
            }else{
                answer.setUpdateTime(new Date());
                mapper.update(answer);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 批量保存
     */
    public void saveBatch(List<Answer> list){
        try{
            Assert.notEmpty(list, "答案列表不能为空");

            for(Answer answer : list){
                Assert.hasText(answer.getLibraryId(), "题库ID不能为空");

                answer.setId(UUIDGenerator.by32());
                answer.setCreateTime(new Date());
            }

            mapper.saveBatch(list);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 批量更新
     */
    public void updateBatch(List<Answer> list){
        try{
            Assert.notEmpty(list, "答案列表不能为空");

            for(Answer answer : list){
                Assert.hasText(answer.getId(), "答案ID不能为空");

                answer.setUpdateTime(new Date());
            }

            mapper.updateBatch(list);
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
            Assert.hasText(id, "答案ID不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
