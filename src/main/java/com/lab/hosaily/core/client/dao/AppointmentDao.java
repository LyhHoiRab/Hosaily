package com.lab.hosaily.core.client.dao;

import com.lab.hosaily.core.client.dao.mapper.AppointmentMapper;
import com.lab.hosaily.core.client.entity.Appointment;
import com.rab.babylon.commons.security.exception.DataAccessException;
import com.rab.babylon.commons.security.mybatis.Criteria;
import com.rab.babylon.commons.security.mybatis.Restrictions;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.UUIDGenerator;
import com.rab.babylon.core.consts.entity.Sex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

@Repository
public class AppointmentDao{

    private static Logger logger = LoggerFactory.getLogger(AppointmentDao.class);

    @Autowired
    private AppointmentMapper mapper;

    /**
     * 保存或更新
     */
    public void saveOrUpdate(Appointment appointment){
        try{
            Assert.notNull(appointment, "预约信息不能为空");

            if(StringUtils.isBlank(appointment.getId())){
                Assert.hasText(appointment.getOrganizationId(), "企业ID不能为空");
                Assert.hasText(appointment.getName(), "客户名称不能为空");
                Assert.hasText(appointment.getPhone(), "客户联系方式不能为空");

                appointment.setId(UUIDGenerator.by32());
                appointment.setCreateTime(new Date());
                mapper.save(appointment);
            }else{
                appointment.setUpdateTime(new Date());
                mapper.update(appointment);
            }
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Appointment getById(String id){
        try{
            Assert.hasText(id, "预约信息ID不能为空");

            Criteria criteria = new Criteria();
            criteria.and(Restrictions.eq("id", id));

            return mapper.getByParams(criteria);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public Page<Appointment> page(PageRequest pageRequest, String organizationId, String name, String wechat, String phone, Sex sex, String type){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            Criteria criteria = new Criteria();
            criteria.limit(Restrictions.limit(pageRequest.getOffset(), pageRequest.getPageSize()));
            criteria.sort(Restrictions.desc("createTime"));

            if(!StringUtils.isBlank(organizationId)){
                criteria.and(Restrictions.eq("organizationId", organizationId));
            }
            if(!StringUtils.isBlank(name)){
                criteria.and(Restrictions.like("name", name));
            }
            if(!StringUtils.isBlank(wechat)){
                criteria.and(Restrictions.like("wechat", wechat));
            }
            if(!StringUtils.isBlank(phone)){
                criteria.and(Restrictions.like("phone", phone));
            }
            if(!StringUtils.isBlank(type)){
                criteria.and(Restrictions.like("type", type));
            }
            if(sex != null){
                criteria.and(Restrictions.eq("sex", sex.getId()));
            }

            List<Appointment> list = mapper.findByParams(criteria);
            Long count = mapper.countByParams(criteria);

            return new Page<Appointment>(list, pageRequest, count);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
