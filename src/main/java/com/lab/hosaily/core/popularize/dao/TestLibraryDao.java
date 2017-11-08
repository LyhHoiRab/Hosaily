package com.lab.hosaily.core.popularize.dao;

import com.lab.hosaily.core.popularize.dao.mapper.AnswerMapper;
import com.lab.hosaily.core.popularize.dao.mapper.TestLibraryMapper;
import com.lab.hosaily.core.popularize.entity.TestLibrary;
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
public class TestLibraryDao{

    private static Logger logger = LoggerFactory.getLogger(TestLibraryDao.class);

    @Autowired
    private TestLibraryMapper mapper;

    @Autowired
    private AnswerMapper answerMapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(TestLibrary library){
        try{
            Assert.notNull(library, "题库信息不能为空");

            if(StringUtils.isBlank(library.getId())){

                library.setId(UUIDGenerator.by32());
                library.setCreateTime(new Date());
                mapper.save(library);
            }else{
                library.setUpdateTime(new Date());
                mapper.update(library);
            }
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
            Assert.hasText(id, "题库ID不能为空");

            mapper.delete(id);
            answerMapper.deleteByTestLibraryId(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询
     */
    public TestLibrary getById(String id){
        try{
            Assert.hasText(id, "题库ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("l.id", id));
            criteria.sort(Restrictions.asc("a.sort"));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<TestLibrary> list(UsingState state, Date createTime, String kind, String title, List<String> ids){
        try{
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("a.sort"));

            if(state != null){
                criteria.and(Restrictions.eq("l.state", state.getId()));
            }
            if(createTime != null){
                criteria.and(Restrictions.eq("l.createTime", createTime));
            }
            if(!StringUtils.isBlank(kind)){
                criteria.and(Restrictions.eq("l.kind", createTime));
            }
            if(!StringUtils.isBlank(title)){
                criteria.and(Restrictions.like("l.title", title));
            }
            if(ids != null && !ids.isEmpty()){
                criteria.and(Restrictions.in("l.id", ids));
            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 分页查询
     */
    public Page<TestLibrary> page(PageRequest pageRequest, UsingState state, Date createTime, String kind, String title){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.asc("a.sort"));

            if(state != null){
                criteria.and(Restrictions.eq("l.state", state.getId()));
            }
            if(createTime != null){
                criteria.and(Restrictions.eq("l.createTime", createTime));
            }
            if(!StringUtils.isBlank(kind)){
                criteria.and(Restrictions.eq("l.kind", createTime));
            }
            if(!StringUtils.isBlank(title)){
                criteria.and(Restrictions.like("l.title", title));
            }

            List<TestLibrary> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<TestLibrary>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询数量
     */
    public Long count(UsingState state, Date createTime, String kind, String title){
        try{
            Criteria criteria = new Criteria();

            if(state != null){
                criteria.and(Restrictions.eq("l.state", state.getId()));
            }
            if(createTime != null){
                criteria.and(Restrictions.eq("l.createTime", createTime));
            }
            if(!StringUtils.isBlank(kind)){
                criteria.and(Restrictions.eq("l.kind", createTime));
            }
            if(!StringUtils.isBlank(title)){
                criteria.and(Restrictions.like("l.title", title));
            }

            return mapper.countByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
