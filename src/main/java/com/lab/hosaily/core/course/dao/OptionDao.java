package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.OptionMapper;
import com.lab.hosaily.core.course.entity.Option;
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
public class OptionDao {

    private static Logger logger = LoggerFactory.getLogger(OptionDao.class);

    @Autowired
    private OptionMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Option option){
        try{
            if(StringUtils.isBlank(option.getId())){
                Assert.hasText(option.getOrganizationId(), "企业ID不能为空");
                option.setId(UUIDGenerator.by32());
                option.setCreateTime(new Date());
                mapper.save(option);
            }else{
                option.setUpdateTime(new Date());
                mapper.update(option);
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
            Assert.hasText(id, "id");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询标签
     */
    public Option getById(String id){
        try{
            Assert.hasText(id, "标签id不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("o.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Option> list(UsingState state){
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
    public Page<Option> page(PageRequest pageRequest, String title, String projectId, String questionId, String questionOption, String organizationId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("p.num"));
            criteria.sort(Restrictions.asc("q.num"));
            criteria.sort(Restrictions.asc("o.questionOption"));
            if(!StringUtils.isBlank(title)){
                criteria.and(Restrictions.like("o.title", title));
            }
            if(!StringUtils.isBlank(projectId)){
                criteria.and(Restrictions.like("o.projectId", projectId));
            }
            if(!StringUtils.isBlank(questionId)){
                criteria.and(Restrictions.like("o.questionId", questionId));
            }
            if(!StringUtils.isBlank(questionOption)){
                criteria.and(Restrictions.like("o.questionOption", questionOption));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.like("o.organizationId", organizationId));
            }
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<Option> list = mapper.findByParams(criteria);

            return new Page<Option>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    /**
     * 查询列表
     */
    public List<Option> list(String questinId) {
        try {
            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("o.questionOption"));
            if(!StringUtils.isBlank(questinId)){
                criteria.and(Restrictions.like("o.questionId", questinId));
            }
            return mapper.findByParams(criteria);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
