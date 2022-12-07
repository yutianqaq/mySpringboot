package com.example.springboot.common;

import lombok.Data;

@Data
public class Result {

    private int code;
    private String message = "success";
    private Object data;

    public Result setCode(ResultCode resultCode){
        this.code = resultCode.code;
        return this;
    }

    public Result setMessage(String message){
        this.message = message;
        return this;
    }

    public Result setData(Object data){
        this.data = data;
        return this;
    }

}
