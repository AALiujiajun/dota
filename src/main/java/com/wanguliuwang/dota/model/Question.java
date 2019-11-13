package com.wanguliuwang.dota.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String Description;
    private  Long gmt_create;
    private  Long gmt_modified;
    private Integer creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private  String tag;

}