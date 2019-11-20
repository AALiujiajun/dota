package com.wanguliuwang.dota.Mapper;

import com.wanguliuwang.dota.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);
    int incCommentCount(Question record);
}
