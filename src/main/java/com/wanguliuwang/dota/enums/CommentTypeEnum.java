package com.wanguliuwang.dota.enums;

import com.wanguliuwang.dota.model.Question;

public enum  CommentTypeEnum {

    Question(1),
    COMMENT(2)
    ;
    private  Integer type;

    public static boolean isExist(Integer type) {
        for(CommentTypeEnum c:CommentTypeEnum.values())
        {
            if(c.getType()==type)
            {
                return  true;
            }

        }
        return  false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
