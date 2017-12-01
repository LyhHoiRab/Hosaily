package com.lab.hosaily.core.handler.utils.handler;

import com.rab.babylon.core.consts.entity.Sex;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class SexEditor extends PropertyEditorSupport{

    @Override
    public void setAsText(String text) throws IllegalArgumentException{
        if(!StringUtils.isBlank(text)){
            int id = Integer.parseInt(text);
            setValue(Sex.getById(id));
        }
    }
}
