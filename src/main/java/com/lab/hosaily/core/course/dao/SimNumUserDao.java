package com.lab.hosaily.core.course.dao;

import com.lab.hosaily.core.course.dao.mapper.SimNumUserMapper;
import com.lab.hosaily.core.course.entity.SimNumUser;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
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
public class SimNumUserDao {

    private static Logger logger = LoggerFactory.getLogger(SimNumUserDao.class);

    @Autowired
    private SimNumUserMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(SimNumUser simNumUser){
        try{
            Assert.notNull(simNumUser, "标签信息不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("sim", simNumUser.getSim()));
            SimNumUser simNumUserByParams = mapper.getByParams(criteria);
            System.out.println("AAAAAAAAAAAAA: " + simNumUserByParams);
            if(null == simNumUserByParams){
                simNumUser.setCreateTime(new Date());
                mapper.save(simNumUser);
            }else{
                simNumUser.setUpdateTime(new Date());
                mapper.update(simNumUser);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 删除标签
     */
    public void delete(String sim){
        try{
            Assert.hasText(sim, "标签sim不能为空");

            mapper.delete(sim);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 根据ID查询标签
     */
    public SimNumUser getBySim(String sim){
        try{
            Assert.hasText(sim, "标签sim不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("sim", sim));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    /**
     * 查询列表
     */
    public List<SimNumUser> list(UsingState state){
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
    public Page<SimNumUser> page(PageRequest pageRequest, String num, String userType, String organizationId){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            if(!StringUtils.isBlank(num)){
                criteria.and(Restrictions.like("num", num));
            }
            if(!StringUtils.isBlank(userType)){
                criteria.and(Restrictions.like("userType", userType));
            }
            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.like("organizationId", organizationId));
            }
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));

            Long count = mapper.countByParams(criteria);
            List<SimNumUser> list = mapper.findByParams(criteria);

            return new Page<SimNumUser>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
