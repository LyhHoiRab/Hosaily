package com.lab.hosaily.core.handler.utils.handler;

import com.lab.hosaily.core.client.consts.AppointmentState;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class AppointmentStateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException{
        if(!StringUtils.isBlank(text)){
            int id = Integer.parseInt(text);
            setValue(AppointmentState.getById(id));
        }
    }
}
