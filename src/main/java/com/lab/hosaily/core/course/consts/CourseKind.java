package com.lab.hosaily.core.course.consts;

import com.google.gson.annotations.SerializedName;

public enum CourseKind{

    @SerializedName("0")
    COURSE(0, "课程"),
    @SerializedName("1")
    POST(1, "帖子");

    private int id;
    private String description;

    private CourseKind(int id, String description){
        this.id = id;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public static CourseKind getById(int id){
        for(CourseKind kind : CourseKind.values()){
            if(kind.getId() == id){
                return kind;
            }
        }

        throw new IllegalArgumentException("未知的课程类型");
    }
}
