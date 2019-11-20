package com.wanguliuwang.dota.exception;

public class CustomerException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomerException(ICustomerErrorCode errorCode) {
        this.code=errorCode.getCode();
       this.message=errorCode.getMessage();

    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
