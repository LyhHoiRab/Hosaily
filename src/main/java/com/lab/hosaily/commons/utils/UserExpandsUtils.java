package com.lab.hosaily.commons.utils;

import com.rab.babylon.core.account.entity.UserExpand;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserExpandsUtils{

    public UserExpandsUtils(){

    }

    public static UserExpand getExpandByName(List<UserExpand> expands, String name){
        if(StringUtils.isBlank(name)){
            return null;
        }
        if(expands == null || expands.isEmpty()){
            return null;
        }

        for(UserExpand expand : expands){
            if(expand.getName().equals(name)){
                return expand;
            }
        }

        return null;
    }

    public static List<UserExpand> findExpandByName(List<UserExpand> expands, String name){
        if(StringUtils.isBlank(name)){
            return null;
        }
        if(expands == null || expands.isEmpty()){
            return null;
        }

        List<UserExpand> list = new ArrayList<UserExpand>();

        for(UserExpand expand : expands){
            if(expand.getName().equals(name)){
                list.add(expand);
            }
        }

        return list;
    }
}
