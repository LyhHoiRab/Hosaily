package com.lab.hosaily.core.client.service;

import com.lab.hosaily.commons.consts.RedisConsts;
import com.lab.hosaily.commons.utils.RandomStringUtils;
import com.lab.hosaily.commons.utils.YunpianUtils;
import com.lab.hosaily.core.client.consts.AppointmentState;
import com.lab.hosaily.core.client.consts.SMSConfig;
import com.lab.hosaily.core.client.consts.SMSTemplate;
import com.lab.hosaily.core.client.dao.AppointmentDao;
import com.lab.hosaily.core.client.entity.Appointment;
import com.rab.babylon.commons.security.exception.ServiceException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.utils.RedisUtils;
import com.rab.babylon.core.consts.entity.Sex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import redis.clients.jedis.ShardedJedisPool;

import java.text.MessageFormat;
import java.util.ArrayList;

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
    public Page<Appointment> page(PageRequest pageRequest, String organizationId, AppointmentState state, String name, String wechat, String phone, Sex sex, String type){
        try{
            Assert.notNull(pageRequest, "分页信息不能为空");

            return appointmentDao.page(pageRequest, organizationId, state, name, wechat, phone, sex, type);
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
            Assert.hasText(phone, "手机号码不能为空");

            SMSConfig config = SMSConfig.YHQS;
            String captcha = RandomStringUtils.nextNumber(4);
            String template = MessageFormat.format(SMSTemplate.CAPTCHA, config.getCompany(), captcha);

            //缓存
            RedisUtils.save(shardedJedisPool.getResource(), RedisConsts.CAPTCHA_PHONE + phone, captcha, RedisConsts.CAPTCHA_EFFECTIVE_SECOND);
            //发送
            YunpianUtils.sendSms(config.getApiKey(), template, phone);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * 校验验证码
     */
    @Override
    public Boolean validateCaptcha(String phone, String captcha){
        try{
            Assert.hasText(phone, "手机号码不能为空");
            Assert.hasText(captcha, "验证码不能为空");

            //缓存
            String source = RedisUtils.get(shardedJedisPool.getResource(), RedisConsts.CAPTCHA_PHONE + phone, String.class);

            if(StringUtils.isBlank(source)){
                return false;
            }

            if(captcha.equals(source)){
                RedisUtils.delete(shardedJedisPool.getResource(), RedisConsts.CAPTCHA_PHONE + phone);
                return true;
            }

            return false;
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
