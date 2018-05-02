package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.consts.AppointmentState;
import com.lab.hosaily.core.client.entity.Appointment;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.Sex;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;

public interface AppointmentService{

    void save(Appointment appointment);

    void update(Appointment appointment);

    Appointment getById(String id);

    Page<Appointment> page(PageRequest pageRequest, String organizationId, AppointmentState state, String name, String wechat, String phone, Sex sex, String type, String description, Date minTime, Date maxTime);

    boolean exist(String organizationId, AppointmentState state, String name, String wechat, String phone, Sex sex, String type, String description, String url, Date createTime);

    /**
     * 发送验证码
     */
    void sendCaptcha(String phone);

    /**
     * 校验验证码
     */
    Boolean validateCaptcha(String phone, String captcha);

    /**
     * 导出Excel
     */
    XSSFWorkbook export(String organizationId, AppointmentState state, String name, String wechat, String phone, Sex sex, String type, String description, Date minTime, Date maxTime);
}
