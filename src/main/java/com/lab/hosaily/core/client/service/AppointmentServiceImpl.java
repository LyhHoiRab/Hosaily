package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.consts.SMSConfig;
import com.lab.hosaily.core.client.consts.SMSTemplate;
import com.lab.hosaily.core.client.dao.AppointmentDao;
import com.lab.hosaily.core.client.entity.Appointment;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.Sex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import redis.clients.jedis.ShardedJedisPool;

import java.text.MessageFormat;

@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService{

    private static Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    @Transactional(readOnly = false)
    public void save(Appointment appointment){
        try{
            Assert.notNull(appointment, "预约信息不能为空");
            Assert.hasText(appointment.getOrganizationId(), "企业ID不能为空");
            Assert.hasText(appointment.getName(), "客户名称不能为空");
            Assert.hasText(appointment.getPhone(), "客户联系方式不能为空");

            appointmentDao.saveOrUpdate(appointment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void update(Appointment appointment){
        try{
            Assert.notNull(appointment, "预约信息不能为空");
            Assert.hasText(appointment.getId(), "预约信息ID不能为空");

            appointmentDao.saveOrUpdate(appointment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Appointment getById(String id){
        try{
            Assert.hasText(id, "预约信息ID不能为空");

            return appointmentDao.getById(id);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Page<Appointment> page(PageRequest pageRequest, String organizationId, String name, String wechat, String phone, Sex sex, String type){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return appointmentDao.page(pageRequest, organizationId, name, wechat, phone, sex, type);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 发送验证码
     */
    @Override
    public void sendCaptcha(String phone){
        try{
            String template = MessageFormat.format(SMSTemplate.CAPTCHA, SMSConfig.NINE_LAB.getCompany());


        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
