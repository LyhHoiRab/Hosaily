package com.lab.hosaily.core.handler.controller;

import com.lab.hosaily.core.handler.utils.handler.UsingStateEditor;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @InitBinder
    public void initUsingStateBinder(WebDataBinder binder){
        binder.registerCustomEditor(UsingState.class, new UsingStateEditor());
    }
}
