package com.lab.hosaily.core.client.service;

import com.lab.hosaily.core.client.entity.Appointment;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.core.consts.entity.Sex;

public interface AppointmentService{

    void save(Appointment appointment);

    void update(Appointment appointment);

    Appointment getById(String id);

    Page<Appointment> page(PageRequest pageRequest, String organizationId, String name, String wechat, String phone, Sex sex, String type);

    /**
     * 发送验证码
     */
    void sendCaptcha(String phone);
}
