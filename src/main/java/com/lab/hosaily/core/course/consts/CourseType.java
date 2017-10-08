package com.lab.hosaily.core.course.consts;

import com.google.gson.annotations.SerializedName;

public enum CourseType{

    @SerializedName("0")
    CATALOGUE(0, "目录"),
    @SerializedName("1")
    CHAPTER(1, "章"),
    @SerializedName("2")
    SECTION(2, "节");

    private int id;
    private String description;

    private CourseType(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static CourseType getById(int id){
        for(CourseType type : CourseType.values()){
            if(type.getId() == id){
                return type;
            }
        }

        throw new IllegalArgumentException("未知的课程类型");
    }
}
