package com.wanguliuwang.dota.dto;

import com.wanguliuwang.dota.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String Description;
    private  Long gmtCreate;
    private  Long gmtModified;
    private Long creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private  String tag;
    private User user;
}
