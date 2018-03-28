package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.QuestionMapper;
import com.lab.hosaily.core.course.entity.Question;
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
public class QuestionDao {

    private static Logger logger = LoggerFactory.getLogger(QuestionDao.class);

    @Autowired
    private QuestionMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Question question){
        try{
            if(StringUtils.isBlank(question.getId())){
                Assert.hasText(question.getOrganizationId(), "企业ID不能为空");
                question.setId(UUIDGenerator.by32());
                question.setCreateTime(new Date());
                mapper.save(question);
            }else{
                question.setUpdateTime(new Date());
                mapper.update(question);
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
            Assert.hasText(id, "标签id不能为空");

            mapper.delete(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询标签
     */
    public Question getById(String id){
        try{
            Assert.hasText(id, "标签id不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("q.id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Question> list(UsingState state){
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
    public Page<Question> page(PageRequest pageRequest, String num, String title, String projectId, String organizationId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.sort(Restrictions.asc("p.num"));
            criteria.sort(Restrictions.asc("q.num"));
            if(!StringUtils.isBlank(num)){
                criteria.and(Restrictions.like("q.num", num));
            }
            if(!StringUtils.isBlank(title)){
                criteria.and(Restrictions.like("q.title", title));
            }
            if(!StringUtils.isBlank(projectId)){
                criteria.and(Restrictions.like("q.projectId", projectId));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.like("q.organizationId", organizationId));
            }
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<Question> list = mapper.findByParams(criteria);

            return new Page<Question>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<Question> list(String nickname, String name, UsingState state, String organizationId, String organizationToken){
        try{
            Criteria criteria = new Criteria();
//            criteria.sort(Restrictions.asc("p.sort"));
//
//            if(!StringUtils.isBlank(nickname)){
//                criteria.and(Restrictions.like("a.nickname", nickname));
//            }
//            if(!StringUtils.isBlank(name)){
//                criteria.and(Restrictions.like("a.name", name));
//            }
//            if(state != null){
//                criteria.and(Restrictions.eq("a.state", state.getId()));
//            }
//            if(!StringUtils.isBlank(organizationId)){
//                criteria.and(Restrictions.eq("a.organizationId", organizationId));
//            }
//            if(!StringUtils.isBlank(organizationToken)){
//                criteria.and(Restrictions.eq("o.token", organizationToken));
//            }

            return mapper.findByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }


    /**
     * 查询列表
     */
    public Question getFirstQuestion(String projectId, String num, UsingState state, String organizationId, String questionId){
        try{
            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("q.projectId", projectId));
            criteria.and(Restrictions.eq("q.num", 1));
            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public Question getNextQuestion(String projectId, String num, UsingState state, String organizationId, String questionId){
        try{
            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("q.id", questionId));
//            criteria.and(Restrictions.eq("q.num", 1));
            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
