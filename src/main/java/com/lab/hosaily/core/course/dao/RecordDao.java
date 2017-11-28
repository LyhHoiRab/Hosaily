package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.RecordMapper;
import com.lab.hosaily.core.course.entity.Record;
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
public class RecordDao {

    private static Logger logger = LoggerFactory.getLogger(RecordDao.class);

    @Autowired
    private RecordMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Record record){
        try{
            Assert.notNull(record, "标签信息不能为空");

            if(StringUtils.isBlank(record.getId())){
//                Assert.hasText(record.getName(), "标签名称不能为空");

                record.setId(UUIDGenerator.by32());
                record.setCreateTime(new Date());
                mapper.save(record);
            }else{
                record.setUpdateTime(new Date());
                mapper.update(record);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除标签
     */
    public void delete(String id){
        try{
            Assert.hasText(id, "标签ID不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询标签
     */
    public Record getById(String id){
        try{
            Assert.hasText(id, "标签ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Record> list(UsingState state){
        try{
            Criteria criteria = new Criteria();
            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<Record> page(PageRequest pageRequest, String userName, String num, String outGoingNum, String sim){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            if(!StringUtils.isBlank(userName)){
                criteria.and(Restrictions.like("userName", userName));
            }
            if(!StringUtils.isBlank(num)){
                criteria.and(Restrictions.like("num", num));
            }
            if(!StringUtils.isBlank(outGoingNum)){
                criteria.and(Restrictions.like("outGoingNum", outGoingNum));
            }
            if(!StringUtils.isBlank(sim)){
                criteria.and(Restrictions.like("sim", sim));
            }


            Long count = mapper.countByParams(criteria);
            List<Record> list = mapper.findByParams(criteria);

            return new Page<Record>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
