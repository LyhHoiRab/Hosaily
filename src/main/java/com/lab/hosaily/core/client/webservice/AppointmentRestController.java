package com.lab.hosaily.core.client.webservice;

import com.lab.hosaily.core.client.consts.AppointmentState;
import com.lab.hosaily.core.client.entity.Appointment;
import com.lab.hosaily.core.client.service.AppointmentService;
import com.rab.babylon.commons.security.exception.ApplicationException;
import com.rab.babylon.commons.security.response.Page;
import com.rab.babylon.commons.security.response.PageRequest;
import com.rab.babylon.commons.security.response.Response;
import com.rab.babylon.core.consts.entity.Sex;
import org.bouncycastle.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/1.0/appointment")
public class AppointmentRestController{

    private static Logger logger = LoggerFactory.getLogger(AppointmentRestController.class);

    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Appointment> save(@RequestBody Appointment appointment){
        try{
            appointmentService.save(appointment);

            return new Response<Appointment>("保存成功", appointment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Appointment> update(@RequestBody Appointment appointment){
        try{
            appointmentService.update(appointment);

            return new Response<Appointment>("更新成功", appointment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Appointment> getById(@PathVariable("id") String id){
        try{
            Appointment appointment = appointmentService.getById(id);

            return new Response<Appointment>("查询成功", appointment);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Page<Appointment>> page(Long pageNum, Long pageSize, String organizationId, AppointmentState state, String name, String wechat, String phone, Sex sex, String type){
        try{
            PageRequest pageRequest = new PageRequest(pageNum, pageSize);
            Page<Appointment> page = appointmentService.page(pageRequest, organizationId, state, name, wechat, phone, sex, type);

            return new Response<Page<Appointment>>("查询成功", page);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/captcha/send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response sendCaptcha(String phone){
        try{
            appointmentService.sendCaptcha(phone);

            return new Response("验证码发送成功", null);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/captcha/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<Boolean> validateCaptcha(String phone, String captcha){
        try{
            Boolean result = appointmentService.validateCaptcha(phone, captcha);

            return new Response("验证成功", result);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
