package com.wanguliuwang.dota.dto;

import com.wanguliuwang.dota.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
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
    private User user;
}
