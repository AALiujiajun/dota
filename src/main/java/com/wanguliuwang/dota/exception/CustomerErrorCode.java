package com.wanguliuwang.dota.exception;

public enum CustomerErrorCode implements ICustomerErrorCode {
    QUESTION_NOT_FOUND(2001,"您找的问题不在了，要不换个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"未登录不能进行评论，请先登录"),
    SYS_ERROR(2004,"服务冒烟了,凉透了"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你操作的评论不在了"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空");
    ;


    CustomerErrorCode(Integer code ,String message) {
        this.message = message;
        this.code=code;
    }
    private Integer code;
    private String message;
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
