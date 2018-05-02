package com.lab.hosaily.core.handler.controller;

import com.lab.hosaily.core.client.consts.AppointmentState;
import com.lab.hosaily.core.client.consts.PurchaseState;
import com.lab.hosaily.core.course.consts.MediaType;
import com.lab.hosaily.core.handler.utils.handler.*;
import com.rab.babylon.core.consts.entity.Sex;
import com.rab.babylon.core.consts.entity.UsingState;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class PropertyController{

    @InitBinder
    public void initDateBinder(WebDataBinder binder){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @InitBinder
    public void initUsingStateBinder(WebDataBinder binder){
        binder.registerCustomEditor(UsingState.class, new UsingStateEditor());
    }

    @InitBinder
    public void initMediaTypeBinder(WebDataBinder binder){
        binder.registerCustomEditor(MediaType.class, new MediaTypeEditor());
    }

    @InitBinder
    public void initSexBinder(WebDataBinder binder){
        binder.registerCustomEditor(Sex.class, new SexEditor());
    }

    @InitBinder
    public void initPurchaseStateBinder(WebDataBinder binder){
        binder.registerCustomEditor(PurchaseState.class, new PurchaseStateEditor());
    }

    @InitBinder
    public void initAppointmentStateBinder(WebDataBinder binder){
        binder.registerCustomEditor(AppointmentState.class, new AppointmentStateEditor());
    }
}
